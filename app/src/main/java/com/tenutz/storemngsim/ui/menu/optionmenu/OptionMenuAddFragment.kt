package com.tenutz.storemngsim.ui.menu.optionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionCreateRequest
import com.tenutz.storemngsim.databinding.FragmentOptionMenuAddBinding
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
class OptionMenuAddFragment: NavOptionMenuFragment() {

    private var _binding: FragmentOptionMenuAddBinding? = null
    val binding: FragmentOptionMenuAddBinding get() = _binding!!

    val vm: OptionMenuAddViewModel by viewModels()

    private val pVm: OptionMenusViewModel by navGraphViewModels(R.id.navigation_option_menu) {
        defaultViewModelProviderFactory
    }

    private lateinit var imagePickerLauncher: ImagePickerLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagePickerLauncher = registerImagePicker { result: List<Image> ->
            result.getOrNull(0)?.let { it -> vm.setImage(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentOptionMenuAddBinding.inflate(inflater, container, false)

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
        vm.viewEvent.observe(viewLifecycleOwner) { event ->
            event?.getContentIfNotHandled()?.let {
                when (it.first) {
                    OptionMenuAddViewModel.EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                    OptionMenuAddViewModel.EVENT_TOAST -> {
                        MyToast.create(mainActivity(), it.second as String, 80)?.show()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageOptionMenuAddBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageOptionMenuAddHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.imageOptionMenuAddHamburger.setOnClickListener {
            mainActivity().binding.drawerMain.openDrawer(GravityCompat.END)
        }
        binding.btnOptionMenuAddSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateMenuName(binding.editOptionMenuAddName.text.toString(), true)
                    Validator.validateMenuCode( binding.editOptionMenuAddCode.text.toString(), true)
                    Validator.validatePrice(binding.editOptionMenuAddPrice.text.toString(), true)
                },
                onSuccess = {
                    lifecycleScope.launch {
                        vm.createOptionMenu(
                            OptionCreateRequest(
                                image = vm.image.value?.let {
                                    val createFormData = MultipartBody.Part.createFormData(
                                        "image",
                                        it.name,
                                        Compressor.compress(mainActivity(), File(it.path)).asRequestBody("image/jpeg".toMediaTypeOrNull())
                                    )
                                    createFormData
                                },
                                optionCode = binding.editOptionMenuAddCode.text.toString(),
                                optionName = binding.editOptionMenuAddName.text.toString(),
                                price = binding.editOptionMenuAddPrice.text.toString().toInt(),
                                use = binding.radiogroupOptionMenuAdd.checkedRadioButtonId == R.id.radio_option_menu_add_use,
                            )
                        ) {
                            pVm.optionMenus()
                        }

                    }
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                },
            )
        }
        binding.imageOptionMenuAddThumbnail.setOnClickListener {
            imagePickerLauncher.launch(ImagePickerConfig { mode = ImagePickerMode.SINGLE })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}