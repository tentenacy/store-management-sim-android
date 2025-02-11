package com.tenutz.storemngsim.ui.menu.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.tenutz.storemngsim.data.datasource.api.dto.menu.MainMenuCreateRequest
import com.tenutz.storemngsim.databinding.FragmentMainMenuAddBinding
import com.tenutz.storemngsim.ui.common.DatePickerDialog
import com.tenutz.storemngsim.ui.common.NumberPickerDialog
import com.tenutz.storemngsim.ui.menu.mainmenu.MainMenuAddViewModel.Companion.EVENT_NAVIGATE_UP
import com.tenutz.storemngsim.ui.menu.mainmenu.MainMenuAddViewModel.Companion.EVENT_TOAST
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
class MainMenuAddFragment : NavMainMenuFragment() {

    private var _binding: FragmentMainMenuAddBinding? = null
    val binding: FragmentMainMenuAddBinding get() = _binding!!

    val vm: MainMenuAddViewModel by viewModels()

    private val pVm: MainMenusViewModel by navGraphViewModels(R.id.navigation_main_menu) {
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

        _binding = FragmentMainMenuAddBinding.inflate(inflater, container, false)

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
                    EVENT_NAVIGATE_UP -> {
                        findNavController().navigateUp()
                    }
                    EVENT_TOAST -> {
                        MyToast.create(mainActivity(), it.second as String, 80)?.show()
                    }
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.imageMainMenuAddBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imageMainMenuAddHome.setOnClickListener {
            mainActivity().navigateToMainFragment()
        }
        binding.btnMainMenuAddSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateMenuName(binding.editMainMenuAddName.text.toString(), true)
                    Validator.validateMenuCode( binding.editMainMenuAddCode.text.toString(), true)
                    Validator.validatePrice(binding.editMainMenuAddPrice.text.toString(), true)
                    Validator.validateDiscountingPrice(binding.editMainMenuAddDiscountedAmount.text.toString())
                    Validator.validateAdditionalPackagingPrice(binding.editMainMenuAddPackagingAmount.text.toString())
                    Validator.validateIngredientDetails(binding.editMainMenuAddIngredientDetails.text.toString())
                    Validator.validateShowDate(binding.editMainMenuAddShowSdate.text.toString(), binding.editMainMenuAddShowEdate.text.toString())
                    Validator.validateNumberData(binding.editMainMenuAddShowShour.text.toString())
                    Validator.validateNumberData(binding.editMainMenuAddShowSmin.text.toString())
                    Validator.validateEventDate(binding.editMainMenuAddEventSdate.text.toString(), binding.editMainMenuAddEventEdate.text.toString())
                    Validator.validateNumberData(binding.editMainMenuAddEventShour.text.toString())
                    Validator.validateNumberData(binding.editMainMenuAddEventSmin.text.toString())
                    Validator.validateMemo(binding.editMainMenuAddMemo.text.toString())
                },
                onSuccess = {
                    lifecycleScope.launch {
                        vm.createMainMenu(
                            request = MainMenuCreateRequest(
                                image = vm.image.value?.let {
                                    val createFormData = MultipartBody.Part.createFormData(
                                        "image",
                                        it.name,
                                        Compressor.compress(mainActivity(), File(it.path)).asRequestBody("image/jpeg".toMediaTypeOrNull())
                                    )
                                    createFormData
                                },
                                menuCode = binding.editMainMenuAddCode.text.toString(),
                                menuName = binding.editMainMenuAddName.text.toString(),
                                price = binding.editMainMenuAddPrice.text.toString().toInt(),
                                discountedPrice = binding.editMainMenuAddDiscountedAmount.text.toString()
                                    .toIntOrNull() ?: 0,
                                additionalPackagingPrice = binding.editMainMenuAddPackagingAmount.text.toString()
                                    .toIntOrNull() ?: 0,
                                packaging = binding.textMainMenuAddPackaging.text.toString().let {
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
                                outOfStock = binding.radiogroupMainMenuAddOos.checkedRadioButtonId != R.id.radio_main_menu_add_oos_not,
                                use = binding.radiogroupMainMenuAdd.checkedRadioButtonId == R.id.radio_main_menu_add_use,
                                ingredientDisplay = binding.radiogroupMainMenuAddIngredient.checkedRadioButtonId == R.id.radio_main_menu_add_show_ingredient,
                                highlightType = binding.textMainMenuAddHighlight.text.toString().let {
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
                                showDateFrom = binding.editMainMenuAddShowSdate.text.toString().replace("-", ""),
                                showDateTo = binding.editMainMenuAddShowEdate.text.toString().replace("-", ""),
                                showTimeFrom = "${binding.editMainMenuAddShowShour.text}${binding.editMainMenuAddShowSmin.text}00",
                                showTimeTo = "${binding.editMainMenuAddShowEhour.text}${binding.editMainMenuAddShowEmin.text}00",
                                showDayOfWeek = listOfNotNull(
                                    binding.textMainMenuAddShowWeekdayMon.text.toString().takeIf { binding.textMainMenuAddShowWeekdayMon.isChecked },
                                    binding.textMainMenuAddShowWeekdayTue.text.toString().takeIf { binding.textMainMenuAddShowWeekdayTue.isChecked },
                                    binding.textMainMenuAddShowWeekdayWed.text.toString().takeIf { binding.textMainMenuAddShowWeekdayWed.isChecked },
                                    binding.textMainMenuAddShowWeekdayThu.text.toString().takeIf { binding.textMainMenuAddShowWeekdayThu.isChecked },
                                    binding.textMainMenuAddShowWeekdayFri.text.toString().takeIf { binding.textMainMenuAddShowWeekdayFri.isChecked },
                                    binding.textMainMenuAddShowWeekdaySat.text.toString().takeIf { binding.textMainMenuAddShowWeekdaySat.isChecked },
                                    binding.textMainMenuAddShowWeekdaySun.text.toString().takeIf { binding.textMainMenuAddShowWeekdaySun.isChecked },
                                ).joinToString(","),
                                eventDateFrom = binding.editMainMenuAddEventSdate.text.toString().replace("-", ""),
                                eventDateTo = binding.editMainMenuAddEventEdate.text.toString().replace("-", ""),
                                eventTimeFrom = "${binding.editMainMenuAddEventShour.text}${binding.editMainMenuAddEventSmin.text}00",
                                eventTimeTo = "${binding.editMainMenuAddEventEhour.text}${binding.editMainMenuAddEventEmin.text}00",
                                eventDayOfWeek = listOfNotNull(
                                    binding.textMainMenuAddEventWeekdayMon.text.toString().takeIf { binding.textMainMenuAddEventWeekdayMon.isChecked },
                                    binding.textMainMenuAddEventWeekdayTue.text.toString().takeIf { binding.textMainMenuAddEventWeekdayTue.isChecked },
                                    binding.textMainMenuAddEventWeekdayWed.text.toString().takeIf { binding.textMainMenuAddEventWeekdayWed.isChecked },
                                    binding.textMainMenuAddEventWeekdayThu.text.toString().takeIf { binding.textMainMenuAddEventWeekdayThu.isChecked },
                                    binding.textMainMenuAddEventWeekdayFri.text.toString().takeIf { binding.textMainMenuAddEventWeekdayFri.isChecked },
                                    binding.textMainMenuAddEventWeekdaySat.text.toString().takeIf { binding.textMainMenuAddEventWeekdaySat.isChecked },
                                    binding.textMainMenuAddEventWeekdaySun.text.toString().takeIf { binding.textMainMenuAddEventWeekdaySun.isChecked },
                                ).joinToString(","),
                                memo = binding.editMainMenuAddMemo.text.toString(),
                                ingredientDetails = binding.editMainMenuAddIngredientDetails.text.toString(),
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
        binding.imageMainMenuAddThumbnail.setOnClickListener {
            imagePickerLauncher.launch(ImagePickerConfig { mode = ImagePickerMode.SINGLE })
        }
        binding.textMainMenuAddPackaging.setOnClickListener {
            val values = listOf("매장,포장", "매장 전용", "포장 전용")
            NumberPickerDialog(
                values = values,
                onClickListener = { id, value ->
                    when(id) {
                        R.id.text_dlgnumber_picker -> {
                            binding.textMainMenuAddPackaging.text = values[(value as Int)]
                        }
                    }
                }
            ).show(childFragmentManager, "numberPickerDialog")
        }
        binding.textMainMenuAddHighlight.setOnClickListener {
            val values = listOf("미사용", "신규", "추천", "인기", "행사")
            NumberPickerDialog(
                values = values,
                onClickListener = { id, value ->
                    when(id) {
                        R.id.text_dlgnumber_picker -> {
                            binding.textMainMenuAddHighlight.text = values[(value as Int)]
                        }
                    }
                }
            ).show(childFragmentManager, "numberPickerDialog")
        }
        binding.editMainMenuAddShowSdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editMainMenuAddShowSdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editMainMenuAddShowSdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editMainMenuAddShowEdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editMainMenuAddShowEdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editMainMenuAddShowEdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editMainMenuAddEventSdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editMainMenuAddEventSdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editMainMenuAddEventSdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editMainMenuAddEventEdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editMainMenuAddEventEdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editMainMenuAddEventEdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}