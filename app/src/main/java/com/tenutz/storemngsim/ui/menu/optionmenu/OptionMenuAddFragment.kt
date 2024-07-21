package com.tenutz.storemngsim.ui.menu.optionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionCreateRequest
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.common.DatePickerDialog
import com.tenutz.storemngsim.ui.common.NumberPickerDialog
import com.tenutz.storemngsim.ui.menu.mainmenu.MainMenuAddViewModel
import com.tenutz.storemngsim.utils.MyToast
import com.tenutz.storemngsim.utils.ext.localDateFrom
import com.tenutz.storemngsim.utils.ext.mainActivity
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
class OptionMenuAddFragment: Fragment() {

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
            findNavController().navigate(R.id.action_global_mainFragment)
        }
        binding.btnOptionMenuAddSave.setOnClickListener {
            Validator.validate(
                onValidation = {
                    Validator.validateMenuName(binding.editOptionMenuAddName.text.toString(), true)
                    Validator.validateMenuCode( binding.editOptionMenuAddCode.text.toString(), true)
                    Validator.validatePrice(binding.editOptionMenuAddPrice.text.toString(), true)
                    Validator.validateDiscountingPrice(binding.editOptionMenuAddDiscountedAmount.text.toString())
                    Validator.validateAdditionalPackagingPrice(binding.editOptionMenuAddPackagingAmount.text.toString())
                    Validator.validateShowDate(binding.editOptionMenuAddShowSdate.text.toString(), binding.editOptionMenuAddShowEdate.text.toString())
                    Validator.validateNumberData(binding.editOptionMenuAddShowShour.text.toString())
                    Validator.validateNumberData(binding.editOptionMenuAddShowSmin.text.toString())
                    Validator.validateEventDate(binding.editOptionMenuAddEventSdate.text.toString(), binding.editOptionMenuAddEventEdate.text.toString())
                    Validator.validateNumberData(binding.editOptionMenuAddEventShour.text.toString())
                    Validator.validateNumberData(binding.editOptionMenuAddEventSmin.text.toString())
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
                                discountedPrice = binding.editOptionMenuAddDiscountedAmount.text.toString()
                                    .toIntOrNull() ?: 0,
                                additionalPackagingPrice = binding.editOptionMenuAddPackagingAmount.text.toString()
                                    .toIntOrNull() ?: 0,
                                packaging = binding.textOptionMenuAddPackaging.text.toString().let {
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
                                outOfStock = binding.radiogroupOptionMenuAddOos.checkedRadioButtonId != R.id.radio_option_menu_add_oos_not,
                                use = binding.radiogroupOptionMenuAdd.checkedRadioButtonId == R.id.radio_option_menu_add_use,
                                optionNameKor = binding.editOptionMenuAddName.text.toString(),
                                showDateFrom = binding.editOptionMenuAddShowSdate.text.toString().replace("-", ""),
                                showDateTo = binding.editOptionMenuAddShowEdate.text.toString().replace("-", ""),
                                showTimeFrom = "${binding.editOptionMenuAddShowShour.text}${binding.editOptionMenuAddShowSmin.text}00",
                                showTimeTo = "${binding.editOptionMenuAddShowEhour.text}${binding.editOptionMenuAddShowEmin.text}00",
                                showDayOfWeek = listOfNotNull(
                                    binding.textOptionMenuAddShowWeekdayMon.text.toString().takeIf { binding.textOptionMenuAddShowWeekdayMon.isChecked },
                                    binding.textOptionMenuAddShowWeekdayTue.text.toString().takeIf { binding.textOptionMenuAddShowWeekdayTue.isChecked },
                                    binding.textOptionMenuAddShowWeekdayWed.text.toString().takeIf { binding.textOptionMenuAddShowWeekdayWed.isChecked },
                                    binding.textOptionMenuAddShowWeekdayThu.text.toString().takeIf { binding.textOptionMenuAddShowWeekdayThu.isChecked },
                                    binding.textOptionMenuAddShowWeekdayFri.text.toString().takeIf { binding.textOptionMenuAddShowWeekdayFri.isChecked },
                                    binding.textOptionMenuAddShowWeekdaySat.text.toString().takeIf { binding.textOptionMenuAddShowWeekdaySat.isChecked },
                                    binding.textOptionMenuAddShowWeekdaySun.text.toString().takeIf { binding.textOptionMenuAddShowWeekdaySun.isChecked },
                                ).joinToString(","),
                                eventDateFrom = binding.editOptionMenuAddEventSdate.text.toString().replace("-", ""),
                                eventDateTo = binding.editOptionMenuAddEventEdate.text.toString().replace("-", ""),
                                eventTimeFrom = "${binding.editOptionMenuAddEventShour.text}${binding.editOptionMenuAddEventSmin.text}00",
                                eventTimeTo = "${binding.editOptionMenuAddEventEhour.text}${binding.editOptionMenuAddEventEmin.text}00",
                                eventDayOfWeek = listOfNotNull(
                                    binding.textOptionMenuAddEventWeekdayMon.text.toString().takeIf { binding.textOptionMenuAddEventWeekdayMon.isChecked },
                                    binding.textOptionMenuAddEventWeekdayTue.text.toString().takeIf { binding.textOptionMenuAddEventWeekdayTue.isChecked },
                                    binding.textOptionMenuAddEventWeekdayWed.text.toString().takeIf { binding.textOptionMenuAddEventWeekdayWed.isChecked },
                                    binding.textOptionMenuAddEventWeekdayThu.text.toString().takeIf { binding.textOptionMenuAddEventWeekdayThu.isChecked },
                                    binding.textOptionMenuAddEventWeekdayFri.text.toString().takeIf { binding.textOptionMenuAddEventWeekdayFri.isChecked },
                                    binding.textOptionMenuAddEventWeekdaySat.text.toString().takeIf { binding.textOptionMenuAddEventWeekdaySat.isChecked },
                                    binding.textOptionMenuAddEventWeekdaySun.text.toString().takeIf { binding.textOptionMenuAddEventWeekdaySun.isChecked },
                                ).joinToString(","),
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
        binding.textOptionMenuAddPackaging.setOnClickListener {
            val values = listOf("매장,포장", "매장 전용", "포장 전용")
            NumberPickerDialog(
                values = values,
                onClickListener = { id, value ->
                    when(id) {
                        R.id.text_dlgnumber_picker -> {
                            binding.textOptionMenuAddPackaging.text = values[(value as Int)]
                        }
                    }
                }
            ).show(childFragmentManager, "numberPickerDialog")
        }
        binding.editOptionMenuAddShowSdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editOptionMenuAddShowSdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editOptionMenuAddShowSdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editOptionMenuAddShowEdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editOptionMenuAddShowEdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editOptionMenuAddShowEdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editOptionMenuAddEventSdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editOptionMenuAddEventSdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editOptionMenuAddEventSdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editOptionMenuAddEventEdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editOptionMenuAddEventEdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editOptionMenuAddEventEdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}