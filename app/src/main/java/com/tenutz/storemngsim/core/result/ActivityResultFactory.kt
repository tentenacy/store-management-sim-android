package com.tenutz.storemngsim.core.result

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult

class ActivityResultFactory<Input, Result> private constructor(
    caller: ActivityResultCaller,
    contract: ActivityResultContract<Input, Result>,
    private var onActivityResult: ((Result) -> Unit)?
) {

    private val launcher: ActivityResultLauncher<Input>
    fun setOnActivityResult(onActivityResult: ((Result) -> Unit)?) {
        this.onActivityResult = onActivityResult
    }

    /**
     * Launch activity, same as [ActivityResultLauncher.launch] except that it allows a callback
     * executed after receiving a result from the target activity.
     */

    /**
     * Same as [.launch] with last parameter set to `null`.
     */
    @JvmOverloads
    fun launch(input: Input, onActivityResult: ((Result) -> Unit)? = this.onActivityResult) {
        if (onActivityResult != null) {
            this.onActivityResult = onActivityResult
        }
        launcher.launch(input)
    }

    private fun callOnActivityResult(result: Result) {
        if (onActivityResult != null) onActivityResult!!(result)
    }

    companion object {

        /**
         * Register activity result using a [ActivityResultContract] and an in-place activity result callback like
         * the default approach. You can still customise callback using [.launch].
         */
        fun <Input, Result> registerForActivityResult(
            caller: ActivityResultCaller,
            contract: ActivityResultContract<Input, Result>,
            onActivityResult: ((Result) -> Unit)?
        ): ActivityResultFactory<Input, Result> {
            return ActivityResultFactory(caller, contract, onActivityResult)
        }

        /**
         * Same as [.registerForActivityResult] except
         * the last argument is set to `null`.
         */
        fun <Input, Result> registerForActivityResult(
            caller: ActivityResultCaller,
            contract: ActivityResultContract<Input, Result>
        ): ActivityResultFactory<Input, Result> {
            return registerForActivityResult(caller, contract, null)
        }

        /**
         * Specialised method for launching new activities.
         */
        fun registerActivityForResult(
            caller: ActivityResultCaller
        ): ActivityResultFactory<Intent, ActivityResult> {
            return registerForActivityResult(caller, StartActivityForResult())
        }
    }

    init {
        launcher = caller.registerForActivityResult(
            contract
        ) { result: Result -> callOnActivityResult(result) }
    }
}