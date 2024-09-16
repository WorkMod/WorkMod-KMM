package app.workmod.workmod_kmm.profile.presentation

import app.workmod.workmod_kmm.BaseViewModel
import app.workmod.workmod_kmm.common.BoolState
import app.workmod.workmod_kmm.profile.domain.AddProfileUseCase
import app.workmod.workmod_kmm.profile.domain.DeleteProfileUseCase
import app.workmod.workmod_kmm.profile.domain.GetAllProfilesUseCase
import app.workmod.workmod_kmm.profile.domain.GetProfileUseCase
import app.workmod.workmod_kmm.profile.domain.UpdateProfileUseCase
import app.workmod.workmod_kmm.profile.domain.model.Education
import app.workmod.workmod_kmm.profile.domain.model.Employment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val getAllProfilesUseCase: GetAllProfilesUseCase,
    private val addProfileUseCase: AddProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase
) : BaseViewModel() {

    private val _getProfileResult = MutableStateFlow(GetProfileResult())
    val getProfileResult = _getProfileResult.asStateFlow()
    private var getProfileJob: Job? = null

    private val _getAllProfilesResult = MutableStateFlow(GetAllProfilesResult())
    val getAllProfilesResult = _getAllProfilesResult.asStateFlow()
    private var getAllProfilesJob: Job? = null

    private val _addProfileResult = MutableStateFlow(BoolState())
    val addProfileResult = _addProfileResult.asStateFlow()
    private var addProfileJob: Job? = null

    private val _updateProfileResult = MutableStateFlow(BoolState())
    val updateProfileResult = _updateProfileResult.asStateFlow()
    private var updateProfileJob: Job? = null

    private val _deleteProfileResult = MutableStateFlow(BoolState())
    val deleteProfileResult = _deleteProfileResult.asStateFlow()
    private var deleteProfileJob: Job? = null

    fun getProfile(profileId: String) {
        getProfileJob?.cancel()
        getProfileJob = scope.launch(Dispatchers.IO) {
            try {
                val response = getProfileUseCase.get(profileId)
                if (response.code == 200) {
                    _getProfileResult.emit(
                        GetProfileResult(
                            success = true,
                            profile = response.data
                        )
                    )
                } else {
                    _getProfileResult.emit(GetProfileResult(error = response.message))
                }
            } catch (e: Exception) {
                _getProfileResult.emit(GetProfileResult(error = e.message ?: ""))
            }
        }
    }

    fun getProfileReset() {
        _getProfileResult.value = GetProfileResult()
    }

    fun getAllProfiles() {
        getAllProfilesJob?.cancel()
        getAllProfilesJob = scope.launch(Dispatchers.IO) {
            try {
                val response = getAllProfilesUseCase.get()
                if (response.code == 200) {
                    _getAllProfilesResult.emit(
                        GetAllProfilesResult(
                            success = true,
                            profiles = response.data ?: listOf()
                        )
                    )
                } else {
                    _getAllProfilesResult.emit(GetAllProfilesResult(error = response.message))
                }
            } catch (e: Exception) {
                _getAllProfilesResult.emit(GetAllProfilesResult(error = e.message ?: ""))
            }
        }
    }

    fun getAllProfilesReset() {
        _getAllProfilesResult.value = GetAllProfilesResult()
    }

    fun addProfile(
        title: String,
        name: String,
        designation: String,
        email: String,
        employments: List<Employment>,
        educations: List<Education>,
        phone: String,
        address: String,
        nationality: String,
        description: String
    ) {
        println("ProfileViewModel addProfile() 1")
        //addProfileJob?.cancel()
        println("ProfileViewModel addProfile() 2")
        try {
            println("ProfileViewModel addProfile() scopeActive:${scope.isActive}")

            scope.launch(Dispatchers.IO) {
                println("ProfileViewModel addProfile() 4")
                try {
                    println("ProfileViewModel addProfile() 5")
                    val response = addProfileUseCase.get(
                        title,
                        name,
                        designation,
                        email,
                        employments,
                        educations,
                        phone,
                        address,
                        nationality,
                        description
                    )
                    println("ProfileViewModel addProfile() 6 ${response.statusCode}" )
                    if (response.statusCode == 201) {
                        _addProfileResult.emit(BoolState(success = true, false))
                    } else {
                        _addProfileResult.emit(BoolState(error = response.message))
                    }
                } catch (e: Exception) {
                    println("ProfileViewModel addProfile() 7 ${e.message}")
                    _addProfileResult.emit(BoolState(error = e.message ?: ""))
                }
            }
            println("ProfileViewModel addProfile() 8")
        } catch (e: Exception) {
            println("ProfileViewModel addProfile() 9 ${e.message}")
            println(e.message)
        }
    }

    fun addProfileReset() {
        _addProfileResult.value = BoolState()
    }

    fun updateProfile(
        profileId: String,
        title: String,
        name: String,
        designation: String,
        email: String,
        employments: List<Employment>,
        educations: List<Education>,
        phone: String,
        address: String,
        nationality: String,
        description: String
    ) {
        updateProfileJob?.cancel()
        updateProfileJob = scope.launch(Dispatchers.IO) {
            try {
                val response = updateProfileUseCase.get(
                    profileId,
                    title,
                    name,
                    designation,
                    email,
                    employments,
                    educations,
                    phone,
                    address,
                    nationality,
                    description
                )
                if (response.statusCode == 200) {
                    _updateProfileResult.emit(BoolState(success = true, false))
                } else {
                    _updateProfileResult.emit(BoolState(error = response.message))
                }
            } catch (e: Exception) {
                _updateProfileResult.emit(BoolState(error = e.message ?: ""))
            }
        }
    }

    fun updateProfileReset() {
        _updateProfileResult.value = BoolState()
    }

    fun deleteProfile(profileId: String) {
        deleteProfileJob?.cancel()
        deleteProfileJob = scope.launch(Dispatchers.IO) {
            try {
                val response = deleteProfileUseCase.get(profileId)
                if (response.statusCode == 200) {
                    _deleteProfileResult.emit(BoolState(success = true, false))
                } else {
                    _deleteProfileResult.emit(BoolState(error = response.message))
                }
            } catch (e: Exception) {
                _deleteProfileResult.emit(BoolState(error = e.message ?: ""))
            }
        }
    }

    fun deleteProfileReset() {
        _deleteProfileResult.value = BoolState()
    }

}