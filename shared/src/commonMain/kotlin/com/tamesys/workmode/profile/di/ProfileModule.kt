package com.tamesys.workmode.profile.di

import com.tamesys.workmode.profile.data.ProfileService
import com.tamesys.workmode.profile.domain.AddProfileUseCase
import com.tamesys.workmode.profile.domain.DeleteProfileUseCase
import com.tamesys.workmode.profile.domain.DownloadProfileUseCase
import com.tamesys.workmode.profile.domain.GetAllProfilesUseCase
import com.tamesys.workmode.profile.domain.GetProfileUseCase
import com.tamesys.workmode.profile.domain.UpdateProfileUseCase
import com.tamesys.workmode.profile.presentation.ProfileViewModel
import org.koin.dsl.module

val profileModule = module {
    single<ProfileService> { ProfileService(get(), get()) }
    single<GetProfileUseCase> { GetProfileUseCase(get()) }
    single<GetAllProfilesUseCase> { GetAllProfilesUseCase(get()) }
    single<AddProfileUseCase> { AddProfileUseCase(get()) }
    single<UpdateProfileUseCase> { UpdateProfileUseCase(get()) }
    single<DeleteProfileUseCase> { DeleteProfileUseCase(get()) }
    single<DownloadProfileUseCase> { DownloadProfileUseCase(get()) }
    single<ProfileViewModel> { ProfileViewModel(get(), get(), get(), get(), get(), get()) }
}