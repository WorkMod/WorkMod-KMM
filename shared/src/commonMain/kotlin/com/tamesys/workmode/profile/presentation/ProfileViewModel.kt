package com.tamesys.workmode.profile.presentation

import com.tamesys.workmode.BaseViewModel
import com.tamesys.workmode.common.BoolState
import com.tamesys.workmode.common.FileWriter
import com.tamesys.workmode.profile.domain.AddProfileUseCase
import com.tamesys.workmode.profile.domain.DeleteProfileUseCase
import com.tamesys.workmode.profile.domain.DownloadProfileUseCase
import com.tamesys.workmode.profile.domain.GetAllProfilesUseCase
import com.tamesys.workmode.profile.domain.GetProfileUseCase
import com.tamesys.workmode.profile.domain.UpdateProfileUseCase
import com.tamesys.workmode.profile.domain.model.Education
import com.tamesys.workmode.profile.domain.model.Employment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val getAllProfilesUseCase: GetAllProfilesUseCase,
    private val addProfileUseCase: AddProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val downloadProfileUseCase: DownloadProfileUseCase,
) : BaseViewModel() {

    private val _getProfileResult = MutableSharedFlow<GetProfileResult>(1)
    val getProfileResult = _getProfileResult.asSharedFlow()
    private var getProfileJob: Job? = null

    private val _getAllProfilesResult = MutableSharedFlow<GetAllProfilesResult>()
    val getAllProfilesResult = _getAllProfilesResult.asSharedFlow()
    private var getAllProfilesJob: Job? = null

    private val _addProfileResult = MutableSharedFlow<BoolState>()
    val addProfileResult = _addProfileResult.asSharedFlow()
    private var addProfileJob: Job? = null

    private val _updateProfileResult = MutableSharedFlow<BoolState>()
    val updateProfileResult = _updateProfileResult.asSharedFlow()
    private var updateProfileJob: Job? = null

    private val _deleteProfileResult = MutableSharedFlow<BoolState>()
    val deleteProfileResult = _deleteProfileResult.asSharedFlow()
    private var deleteProfileJob: Job? = null

    private val _downloadProfileResult = MutableSharedFlow<DownloadProfileResult>()
    val downloadProfileResult = _downloadProfileResult.asSharedFlow()
    private var downloadProfileJob: Job? = null

    private val _newEmploymentsResult = MutableStateFlow<List<Employment>>(listOf())
    val newEmploymentsResult = _newEmploymentsResult.asStateFlow()

    private val _newEducationResult = MutableStateFlow<List<Education>>(listOf())
    val newEducationResult = _newEducationResult.asStateFlow()

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
        addProfileJob?.cancel()
        addProfileJob = scope.launch(Dispatchers.IO) {
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

    fun downloadProfile(profileId: String, dir: String) {
        downloadProfileJob?.cancel()
        downloadProfileJob = scope.launch(Dispatchers.IO) {
            try {
                val fileWriter = FileWriter(dir)
                //fileWriter.setFile("$profileId.txt")
                val response = downloadProfileUseCase.get(fileWriter, profileId)
                if (response.code == 200) {
                    _downloadProfileResult.emit(
                        DownloadProfileResult(success = true,
                        filePath = response.data?.localUrl)
                    )
                } else {
                    _downloadProfileResult.emit(DownloadProfileResult(error = response.message))
                }
            } catch (e: Exception) {
                _downloadProfileResult.emit(DownloadProfileResult(error = e.message ?: ""))
            }
        }
    }

    fun addEmployment(employment: Employment) {
        val newList = newEmploymentsResult.replayCache[0] + listOf(employment)
        scope.launch { _newEmploymentsResult.emit(newList) }
    }

    fun addEducation(education: Education) {
        val newList = newEducationResult.replayCache[0] + listOf(education)
        scope.launch { _newEducationResult.emit(newList) }
    }

}