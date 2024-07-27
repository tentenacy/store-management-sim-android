package com.tenutz.storemngsim.ui.menu.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuUpdateRequest
import com.tenutz.storemngsim.databinding.FragmentMainMenuDetailsBinding
import com.tenutz.storemngsim.ui.common.CommonBindingAdapter
import com.tenutz.storemngsim.ui.common.DatePickerDialog
import com.tenutz.storemngsim.ui.common.NumberPickerDialog
import com.tenutz.storemngsim.ui.menu.mainmenu.base.NavMainMenuFragment
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.localDateFrom
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.navigateToMainFragment
import com.tenutz.storemngsim.utils.ext.toDateFormat
import com.tenutz.storemngsim.utils.validation.Validator
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class MainMenuDetailsFragment: NavMainMenuFragment() {

    private var _binding: FragmentMainMenuDetailsBinding? = null
    val binding: FragmentMainMenuDetailsBinding get() = _binding!!

    val vm: MainMenuDetailsViewModel by viewModels()

    private val pVm: MainMenusViewModel by navGraphViewModels(R.id.navigation_main_menu) {
        defaultViewModelProviderFactory
    }

    private lateinit var imagePickerLauncher: ImagePickerLauncher

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

        _binding = FragmentMainMenuDetailsBinding.inflate(inflater, container, false)

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
            CommonBindingAdapter.showImage(binding.imageMainMenuDetailsThumbnail, it, null)
        }
    }

    private fun setOnClickListeners() {
        binding.imageMainMenuDetailsBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMainMenuDetailsHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.btnMainMenuDetailsCancel.setOnClickListener {
            binding.vm = vm
            vm.switchToReadMode()
        }
        binding.btnMainMenuDetailsSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateMenuName(binding.editMainMenuDetailsName.text.toString(), true)
                    Validator.validatePrice(binding.editMainMenuDetailsPrice.text.toString(), true)
                    Validator.validateDiscountingPrice(binding.editMainMenuDetailsDiscountedAmount.text.toString())
                    Validator.validateAdditionalPackagingPrice(binding.editMainMenuDetailsPackagingAmount.text.toString())
                    Validator.validateIngredientDetails(binding.editMainMenuDetailsIngredientDetails.text.toString())
                    Validator.validateShowDate(binding.editMainMenuDetailsShowSdate.text.toString(), binding.editMainMenuDetailsShowEdate.text.toString())
                    Validator.validateNumberData(binding.editMainMenuDetailsShowShour.text.toString())
                    Validator.validateNumberData(binding.editMainMenuDetailsShowSmin.text.toString())
                    Validator.validateEventDate(binding.editMainMenuDetailsEventSdate.text.toString(), binding.editMainMenuDetailsEventEdate.text.toString())
                    Validator.validateNumberData(binding.editMainMenuDetailsEventShour.text.toString())
                    Validator.validateNumberData(binding.editMainMenuDetailsEventSmin.text.toString())
                    Validator.validateMemo(binding.editMainMenuDetailsMemo.text.toString())
                },
                onSuccess = {
                    lifecycleScope.launch {
                        vm.updateMainMenu(
                            request = MainMenuUpdateRequest(
                                image = vm.image.value?.let {
                                    val createFormData = MultipartBody.Part.createFormData(
                                        "image",
                                        it.name,
                                        Compressor.compress(mainActivity(), File(it.path)).asRequestBody("image/jpeg".toMediaTypeOrNull())
                                    )
                                    createFormData
                                },
                                menuName = binding.editMainMenuDetailsName.text.toString(),
                                price = binding.editMainMenuDetailsPrice.text.toString().toInt(),
                                discountedPrice = binding.editMainMenuDetailsDiscountedAmount.text.toString()
                                    .toIntOrNull() ?: 0,
                                additionalPackagingPrice = binding.editMainMenuDetailsPackagingAmount.text.toString()
                                    .toIntOrNull() ?: 0,
                                packaging = binding.textMainMenuDetailsPackaging.text.toString().let {
                                    when (it) {
                                        "매장 전용" -> {
                                            "02"
                                        }
                                        "포장 전용" -> {
                                            "03"
                                        }
                                        else -> {
                                            "01"
                                        }
                                    }
                                },
                                outOfStock = binding.radiogroupMainMenuDetailsOos.checkedRadioButtonId != R.id.radio_main_menu_details_oos_not,
                                use =
                                when (binding.radiogroupMainMenuDetails.checkedRadioButtonId) {
                                    R.id.radio_main_menu_details_use_not -> {
                                        false
                                    }
                                    R.id.radio_main_menu_details_delete -> {
                                        null
                                    }
                                    else -> {
                                        true
                                    }
                                },
                                ingredientDisplay = binding.radiogroupMainMenuDetailsIngredient.checkedRadioButtonId == R.id.radio_main_menu_details_show_ingredient,
                                highlightType = binding.textMainMenuDetailsHighlight.text.toString().let {
                                    when (it) {
                                        "신규" -> {
                                            "01"
                                        }
                                        "추천" -> {
                                            "02"
                                        }
                                        "인기" -> {
                                            "03"
                                        }
                                        "행사" -> {
                                            "04"
                                        }
                                        else -> {
                                            "00"
                                        }
                                    }
                                },
                                showDateFrom = binding.editMainMenuDetailsShowSdate.text.toString().replace("-", ""),
                                showDateTo = binding.editMainMenuDetailsShowEdate.text.toString().replace("-", ""),
                                showTimeFrom = "${binding.editMainMenuDetailsShowShour.text}${binding.editMainMenuDetailsShowSmin.text}00",
                                showTimeTo = "${binding.editMainMenuDetailsShowEhour.text}${binding.editMainMenuDetailsShowEmin.text}00",
                                showDayOfWeek = listOfNotNull(
                                    binding.textMainMenuDetailsShowWeekdayMon.text.toString().takeIf { binding.textMainMenuDetailsShowWeekdayMon.isChecked },
                                    binding.textMainMenuDetailsShowWeekdayTue.text.toString().takeIf { binding.textMainMenuDetailsShowWeekdayTue.isChecked },
                                    binding.textMainMenuDetailsShowWeekdayWed.text.toString().takeIf { binding.textMainMenuDetailsShowWeekdayWed.isChecked },
                                    binding.textMainMenuDetailsShowWeekdayThu.text.toString().takeIf { binding.textMainMenuDetailsShowWeekdayThu.isChecked },
                                    binding.textMainMenuDetailsShowWeekdayFri.text.toString().takeIf { binding.textMainMenuDetailsShowWeekdayFri.isChecked },
                                    binding.textMainMenuDetailsShowWeekdaySat.text.toString().takeIf { binding.textMainMenuDetailsShowWeekdaySat.isChecked },
                                    binding.textMainMenuDetailsShowWeekdaySun.text.toString().takeIf { binding.textMainMenuDetailsShowWeekdaySun.isChecked },
                                ).joinToString(","),
                                eventDateFrom = binding.editMainMenuDetailsEventSdate.text.toString().replace("-", ""),
                                eventDateTo = binding.editMainMenuDetailsEventEdate.text.toString().replace("-", ""),
                                eventTimeFrom = "${binding.editMainMenuDetailsEventShour.text}${binding.editMainMenuDetailsEventSmin.text}00",
                                eventTimeTo = "${binding.editMainMenuDetailsEventEhour.text}${binding.editMainMenuDetailsEventEmin.text}00",
                                eventDayOfWeek = listOfNotNull(
                                    binding.textMainMenuDetailsEventWeekdayMon.text.toString().takeIf { binding.textMainMenuDetailsEventWeekdayMon.isChecked },
                                    binding.textMainMenuDetailsEventWeekdayTue.text.toString().takeIf { binding.textMainMenuDetailsEventWeekdayTue.isChecked },
                                    binding.textMainMenuDetailsEventWeekdayWed.text.toString().takeIf { binding.textMainMenuDetailsEventWeekdayWed.isChecked },
                                    binding.textMainMenuDetailsEventWeekdayThu.text.toString().takeIf { binding.textMainMenuDetailsEventWeekdayThu.isChecked },
                                    binding.textMainMenuDetailsEventWeekdayFri.text.toString().takeIf { binding.textMainMenuDetailsEventWeekdayFri.isChecked },
                                    binding.textMainMenuDetailsEventWeekdaySat.text.toString().takeIf { binding.textMainMenuDetailsEventWeekdaySat.isChecked },
                                    binding.textMainMenuDetailsEventWeekdaySun.text.toString().takeIf { binding.textMainMenuDetailsEventWeekdaySun.isChecked },
                                ).joinToString(","),
                                memo = binding.editMainMenuDetailsMemo.text.toString(),
                                ingredientDetails = binding.editMainMenuDetailsIngredientDetails.text.toString(),
                            )
                        ) {
                            pVm.mainMenus()
                        }
                    }
                },
                onFailure = { e ->
                    MyToast.create(mainActivity(), e.errorCode.message, 80)?.show()
                },
            )
        }
        binding.imageMainMenuDetailsThumbnail.setOnClickListener {
            if(vm.editMode.value == true) {
                imagePickerLauncher.launch(ImagePickerConfig { mode = ImagePickerMode.SINGLE })
            }
        }
        binding.textMainMenuDetailsPackaging.setOnClickListener {
            val values = listOf("매장,포장", "매장 전용", "포장 전용")
            NumberPickerDialog(
                values = values,
                onClickListener = { id, value ->
                    when(id) {
                        R.id.text_dlgnumber_picker -> {
                            binding.textMainMenuDetailsPackaging.text = values[(value as Int)]
                        }
                    }
                }
            ).show(childFragmentManager, "numberPickerDialog")
        }
        binding.textMainMenuDetailsHighlight.setOnClickListener {
            val values = listOf("미사용", "신규", "추천", "인기", "행사")
            NumberPickerDialog(
                values = values,
                onClickListener = { id, value ->
                    when(id) {
                        R.id.text_dlgnumber_picker -> {
                            binding.textMainMenuDetailsHighlight.text = values[(value as Int)]
                        }
                    }
                }
            ).show(childFragmentManager, "numberPickerDialog")
        }
        binding.editMainMenuDetailsShowSdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editMainMenuDetailsShowSdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editMainMenuDetailsShowSdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editMainMenuDetailsShowEdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editMainMenuDetailsShowEdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editMainMenuDetailsShowEdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editMainMenuDetailsEventSdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editMainMenuDetailsEventSdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editMainMenuDetailsEventSdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editMainMenuDetailsEventEdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editMainMenuDetailsEventEdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editMainMenuDetailsEventEdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}