package com.tenutz.storemngsim.ui.menu.optionmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerLauncher
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.esafirm.imagepicker.model.Image
import com.tenutz.storemngsim.R
import com.tenutz.storemngsim.data.datasource.api.dto.option.OptionUpdateRequest
import com.tenutz.storemngsim.databinding.*
import com.tenutz.storemngsim.ui.common.CommonBindingAdapter
import com.tenutz.storemngsim.ui.common.DatePickerDialog
import com.tenutz.storemngsim.ui.common.NumberPickerDialog
import com.tenutz.storemngsim.utils.ext.localDateFrom
import com.tenutz.storemngsim.utils.ext.mainActivity
import com.tenutz.storemngsim.utils.ext.toDateFormat
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@AndroidEntryPoint
class OptionMenuDetailsFragment: Fragment() {

    private var _binding: FragmentOptionMenuDetailsBinding? = null
    val binding: FragmentOptionMenuDetailsBinding get() = _binding!!

    val args: OptionMenuDetailsFragmentArgs by navArgs()

    val vm: OptionMenuDetailsViewModel by viewModels()

    private val pVm: OptionMenusViewModel by navGraphViewModels(R.id.navigation_option_menu) {
        defaultViewModelProviderFactory
    }

    private lateinit var imagePickerLauncher: ImagePickerLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagePickerLauncher = registerImagePicker { result: List<Image> ->
            result.getOrNull(0)?.let { it -> vm.setImageUri(it) }
        }

        vm.optionMenu(args.optionMenuCode)
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
        binding.btnOptionMenuDetailsCancel.setOnClickListener {
            binding.vm = vm
            vm.switchToReadMode()
        }
        binding.btnOptionMenuDetailsSave.setOnClickListener {
            lifecycleScope.launch {
                vm.updateOptionMenu(
                    args.optionMenuCode,
                    OptionUpdateRequest(
                        image = vm.image.value?.let {
                            val createFormData = MultipartBody.Part.createFormData(
                                "image",
                                it.name,
                                Compressor.compress(mainActivity(), File(it.path)).asRequestBody("image/jpeg".toMediaTypeOrNull())
                            )
                            createFormData
                        },
                        optionName = binding.editOptionMenuDetailsName.text.toString(),
                        price = binding.editOptionMenuDetailsPrice.text.toString().toInt(),
                        discountedPrice = binding.editOptionMenuDetailsDiscountedAmount.text.toString()
                            .toIntOrNull() ?: 0,
                        additionalPackagingPrice = binding.editOptionMenuDetailsPackagingAmount.text.toString()
                            .toIntOrNull() ?: 0,
                        packaging = binding.textOptionMenuDetailsPackaging.text.toString().let {
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
                        outOfStock = binding.radiogroupOptionMenuDetailsOos.checkedRadioButtonId != R.id.radio_option_menu_details_oos_not,
                        use = binding.radiogroupOptionMenuDetails.checkedRadioButtonId == R.id.radio_option_menu_details_use,
                        optionNameKor = binding.editOptionMenuDetailsName.text.toString(),
                        showDateFrom = binding.editOptionMenuDetailsShowSdate.text.toString().replace("-", ""),
                        showDateTo = binding.editOptionMenuDetailsShowEdate.text.toString().replace("-", ""),
                        showTimeFrom = "${binding.editOptionMenuDetailsShowShour.text}${binding.editOptionMenuDetailsShowSmin.text}00",
                        showTimeTo = "${binding.editOptionMenuDetailsShowEhour.text}${binding.editOptionMenuDetailsShowEmin.text}00",
                        showDayOfWeek = listOfNotNull(
                            binding.textOptionMenuDetailsShowWeekdayMon.text.toString().takeIf { binding.textOptionMenuDetailsShowWeekdayMon.isChecked },
                            binding.textOptionMenuDetailsShowWeekdayTue.text.toString().takeIf { binding.textOptionMenuDetailsShowWeekdayTue.isChecked },
                            binding.textOptionMenuDetailsShowWeekdayWed.text.toString().takeIf { binding.textOptionMenuDetailsShowWeekdayWed.isChecked },
                            binding.textOptionMenuDetailsShowWeekdayThu.text.toString().takeIf { binding.textOptionMenuDetailsShowWeekdayThu.isChecked },
                            binding.textOptionMenuDetailsShowWeekdayFri.text.toString().takeIf { binding.textOptionMenuDetailsShowWeekdayFri.isChecked },
                            binding.textOptionMenuDetailsShowWeekdaySat.text.toString().takeIf { binding.textOptionMenuDetailsShowWeekdaySat.isChecked },
                            binding.textOptionMenuDetailsShowWeekdaySun.text.toString().takeIf { binding.textOptionMenuDetailsShowWeekdaySun.isChecked },
                        ).joinToString(","),
                        eventDateFrom = binding.editOptionMenuDetailsEventSdate.text.toString().replace("-", ""),
                        eventDateTo = binding.editOptionMenuDetailsEventEdate.text.toString().replace("-", ""),
                        eventTimeFrom = "${binding.editOptionMenuDetailsEventShour.text}${binding.editOptionMenuDetailsEventSmin.text}00",
                        eventTimeTo = "${binding.editOptionMenuDetailsEventEhour.text}${binding.editOptionMenuDetailsEventEmin.text}00",
                        eventDayOfWeek = listOfNotNull(
                            binding.textOptionMenuDetailsEventWeekdayMon.text.toString().takeIf { binding.textOptionMenuDetailsEventWeekdayMon.isChecked },
                            binding.textOptionMenuDetailsEventWeekdayTue.text.toString().takeIf { binding.textOptionMenuDetailsEventWeekdayTue.isChecked },
                            binding.textOptionMenuDetailsEventWeekdayWed.text.toString().takeIf { binding.textOptionMenuDetailsEventWeekdayWed.isChecked },
                            binding.textOptionMenuDetailsEventWeekdayThu.text.toString().takeIf { binding.textOptionMenuDetailsEventWeekdayThu.isChecked },
                            binding.textOptionMenuDetailsEventWeekdayFri.text.toString().takeIf { binding.textOptionMenuDetailsEventWeekdayFri.isChecked },
                            binding.textOptionMenuDetailsEventWeekdaySat.text.toString().takeIf { binding.textOptionMenuDetailsEventWeekdaySat.isChecked },
                            binding.textOptionMenuDetailsEventWeekdaySun.text.toString().takeIf { binding.textOptionMenuDetailsEventWeekdaySun.isChecked },
                        ).joinToString(","),
                    )
                ) {
                    pVm.optionMenus()
                }
            }
        }
        binding.imageOptionMenuDetailsThumbnail.setOnClickListener {
            if(vm.editMode.value == true) {
                imagePickerLauncher.launch(ImagePickerConfig { mode = ImagePickerMode.SINGLE })
            }
        }
        binding.textOptionMenuDetailsPackaging.setOnClickListener {
            val values = listOf("매장,포장", "매장 전용", "포장 전용")
            NumberPickerDialog(
                values = values,
                onClickListener = { id, value ->
                    when(id) {
                        R.id.text_dlgnumber_picker -> {
                            binding.textOptionMenuDetailsPackaging.text = values[(value as Int)]
                        }
                    }
                }
            ).show(childFragmentManager, "numberPickerDialog")
        }
        binding.editOptionMenuDetailsShowSdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editOptionMenuDetailsShowSdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editOptionMenuDetailsShowSdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editOptionMenuDetailsShowEdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editOptionMenuDetailsShowEdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editOptionMenuDetailsShowEdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editOptionMenuDetailsEventSdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editOptionMenuDetailsEventSdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editOptionMenuDetailsEventSdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
        binding.editOptionMenuDetailsEventEdate.setOnClickListener {
            DatePickerDialog(
                localDateFrom(binding.editOptionMenuDetailsEventEdate.text.toString()),
                onDatePickListener = { date ->
                    binding.editOptionMenuDetailsEventEdate.text = date.toDateFormat()
                }
            ).show(childFragmentManager, "datePickerDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}