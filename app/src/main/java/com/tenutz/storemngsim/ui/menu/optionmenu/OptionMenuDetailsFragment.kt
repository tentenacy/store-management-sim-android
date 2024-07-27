package com.tenutz.storemngsim.ui.menu.optionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionUpdateRequest
import com.tenutz.storemngsim.databinding.FragmentOptionMenuDetailsBinding
import com.tenutz.storemngsim.ui.common.CommonBindingAdapter
import com.tenutz.storemngsim.ui.menu.optionmenu.base.NavOptionMenuFragment
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class OptionMenuDetailsFragment : NavOptionMenuFragment() {

    private var _binding: FragmentOptionMenuDetailsBinding? = null
    val binding: FragmentOptionMenuDetailsBinding get() = _binding!!

    val vm: OptionMenuDetailsViewModel by viewModels()

    private val optionMenusVm: OptionMenusViewModel by navGraphViewModels(R.id.navigation_option_menu) {
        defaultViewModelProviderFactory
    }

    private lateinit var imagePickerLauncher: ImagePickerLauncher

    private val args: OptionMenuDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagePickerLauncher = registerImagePicker { result: List<Image> ->
            result.getOrNull(0)?.let { it -> vm.setImageUri(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionMenuDetailsBinding.inflate(inflater, container, false)

        binding.vm = vm
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observeData()
    }

    private fun observeData() {
        vm.image.observe(viewLifecycleOwner) {
            CommonBindingAdapter.showImage(binding.imageOptionMenuDetailsThumbnail, it, null)
        }
    }

    private fun setOnClickListeners() {
        binding.imageOptionMenuDetailsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOptionMenuDetailsHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOptionMenuDetailsHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnOptionMenuDetailsCancel.setOnClickListener {
            binding.vm = vm
            vm.switchToReadMode()
        }
        binding.btnOptionMenuDetailsSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateMenuName(binding.editOptionMenuDetailsName.text.toString(), true)
                    Validator.validatePrice(binding.editOptionMenuDetailsPrice.text.toString(), true)
                },
                onSuccess = {
                    lifecycleScope.launch {
                        vm.updateOptionMenu(
                            args.optionCode,
                            OptionUpdateRequest(
                                image = vm.image.value?.let {
                                    val createFormData = MultipartBody.Part.createFormData(
                                        "image",
                                        it.name,
                                        Compressor.compress(mainActivity(), File(it.path))
                                            .asRequestBody("image/jpeg".toMediaTypeOrNull())
                                    )
                                    createFormData
                                },
                                optionName = binding.editOptionMenuDetailsName.text.toString(),
                                price = binding.editOptionMenuDetailsPrice.text.toString().toInt(),
                                use =
                                when (binding.radiogroupOptionMenuDetails.checkedRadioButtonId) {
                                    R.id.radio_option_menu_details_use_not -> {
                                        false
                                    }
                                    R.id.radio_option_menu_details_delete -> {
                                        null
                                    }
                                    else -> {
                                        true
                                    }
                                },
                            )
                        ) {
                            optionMenusVm.optionMenus()
                        }
                    }
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                },
            )
        }
        binding.imageOptionMenuDetailsThumbnail.setOnClickListener {
            if (vm.editMode.value == true) {
                imagePickerLauncher.launch(ImagePickerConfig { mode = ImagePickerMode.SINGLE })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}