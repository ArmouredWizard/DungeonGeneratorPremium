package uk.co.maddwarf.randomdungeongeneratorpremium.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.GetInhabitantsUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    inhabitantsUseCase: GetInhabitantsUseCase,
    application: Application
) : AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext

    val sizeList = listOf("Small", "Medium", "Large", "Huge")
    val inhabitantsList: List<String> =
        inhabitantsUseCase.getInhabitantsCategories(context = context, all = true)

}
