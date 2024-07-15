package app.workmod.workmod_kmm.profile.di

import app.workmod.workmod_kmm.profile.data.ProfileService
import app.workmod.workmod_kmm.profile.domain.AddProfileUseCase
import app.workmod.workmod_kmm.profile.domain.DeleteProfileUseCase
import app.workmod.workmod_kmm.profile.domain.GetAllProfilesUseCase
import app.workmod.workmod_kmm.profile.domain.GetProfileUseCase
import app.workmod.workmod_kmm.profile.domain.UpdateProfileUseCase
import app.workmod.workmod_kmm.profile.presentation.ProfileViewModel
import org.koin.dsl.module

val profileModule = module {
    single<ProfileService> { ProfileService(get(), get()) }
    single<GetProfileUseCase> { GetProfileUseCase(get()) }
    single<GetAllProfilesUseCase> { GetAllProfilesUseCase(get()) }
    single<AddProfileUseCase> { AddProfileUseCase(get()) }
    single<UpdateProfileUseCase> { UpdateProfileUseCase(get()) }
    single<DeleteProfileUseCase> { DeleteProfileUseCase(get()) }
    single<ProfileViewModel> { ProfileViewModel(get(), get(), get(), get(), get()) }
}