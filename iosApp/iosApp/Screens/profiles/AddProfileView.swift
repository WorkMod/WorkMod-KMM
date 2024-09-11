//
//  AddProfileView.swift
//  iosApp
//
//  Created by Riyas Edathadathil on 15/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension AddProfilesView {
    
    @MainActor
    class AddProfileViewModelWrapper: ObservableObject {
        
        let profileViewModel: ProfileViewModel
        var profileId: String? = nil
        var onProfileAdded: () -> Void
        var onProfileUpdated: () -> Void
        
        init() {
            self.profileViewModel = ProfileInjector().profileViewModel
            self.onProfileAdded = {}
            self.addProfileResult = profileViewModel.addProfileResult.value
            self.updateProfileResult = profileViewModel.updateProfileResult.value
        }
        
        @Published var addProfileResult: BoolState
        @Published var updateProfileResult: BoolState
        @Published var getProfileResult: GetProfileResult
    
        
        func startObserving() {
            Task {
                for await newState in profileViewModel.addProfileResult {
                    print("new addProfile state! : \(newState.success)")
                    self.addProfileResult = newState
                    if (newState.success) {
                        self.profileViewModel.addProfileReset()
                        self.onProfileAdded()
                    }
                }
                for await newState in profileViewModel.updateProfileResult {
                    print("new updateProfile state! : \(newState.success)")
                    self.updateProfileResult = newState
                    if (newState.success) {
                        self.profileViewModel.updateProfileReset()
                        self.onProfileUpdated()
                    }
                }
                for await newState in profileViewModel.getProfileResult {
                    print("new getProfile state! : \(newState.success)")
                    self.getProfileResult = newState
                }
                
            }
        }
        
        func getProfile() {
            if (self.profileId != nil) {
                self.profileViewModel.getProfile(profileId: self.profileId!)
            }
        }
    
    }
}

struct AddProfilesView: View {
    
    static let tag = "AddProfileView"
    
    @ObservedObject private(set) var viewModel: AddProfileViewModelWrapper
    
    init(viewModel: AddProfileViewModelWrapper, profileId: String?, onProfileAdded: @escaping() -> Void, onProfileUpdated: @escaping() -> Void) {
        self.viewModel = viewModel
        self.viewModel.onProfileAdded = onProfileAdded
        self.viewModel.onProfileUpdated = onProfileUpdated
    }
    
    var body: some View {
        VStack {
            //AllProfilesAppBar()
            
            if (viewModel.getProfileResult.loading || viewModel.addProfileResult.loading || viewModel.updateProfileResult.loading) {
                AddProfileLoader()
            }
            
            if (!viewModel.addProfileResult.error.isEmpty) {
                AllProfilesErrorMessage(message: viewModel.addProfileResult.error)
            }
            
            
            ScrollView {
                ProfileView(profile: viewModel.getProfileResult.profile)
            }
        
            
        }.onAppear{
            self.viewModel.startObserving()
        }.onFirstAppear {
            self.viewModel.getProfile()
        }
    }
}

struct AddProfileAppBar: View {
    var body: some View {
        Text("Add/Update Profile")
            .font(.largeTitle)
            .fontWeight(.bold)
    }
}

struct ProfileView: View {
    var profile: Profile?
    
    @State private var title: String = ""
    @State private var name: String = ""
    @State private var designation: String = ""
    @State private var email: String = ""
    @State private var phone: String = ""
    @State private var address: String = ""
    @State private var nationality: String = ""
    @State private var description: String = ""
    
    init(profile: Profile? = nil) {
        self.profile = profile
        if (profile != nil) {
            self.title = profile!.title
            self.name = profile!.name
            self.designation = profile!.designation
            self.email = profile!.email
            self.phone = profile!.phone
            self.address = profile!.address
            self.nationality = profile!.nationality
            self.description = profile!.description
        }
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {

            TextField(
                "Title",
                text: $title
            ).autocapitalization(.words)
            .disableAutocorrection(true)
            .padding(.top, 20)
            
            TextField(
                "Name",
                text: $name
            ).autocapitalization(.words)
            .disableAutocorrection(true)
            .padding(.top, 20)
            
            TextField(
                "Designation",
                text: $designation
            ).autocapitalization(.words)
            .disableAutocorrection(true)
            .padding(.top, 20)
            
            TextField(
                "Email",
                text: $email
            ).autocapitalization(.words)
            .disableAutocorrection(true)
            .padding(.top, 20)
            
            TextField(
                "Phone",
                text: $phone
            ).autocapitalization(.words)
            .disableAutocorrection(true)
            .padding(.top, 20)
            
            TextField(
                "Address",
                text: $address
            ).autocapitalization(.words)
            .disableAutocorrection(true)
            .padding(.top, 20)
            
            TextField(
                "Nationality",
                text: $nationality
            ).autocapitalization(.words)
            .disableAutocorrection(true)
            .padding(.top, 20)
            
            TextField(
                "Description",
                text: $description
            ).autocapitalization(.words)
            .disableAutocorrection(true)
            .padding(.top, 20)
            
        }
        .padding(16)
    }
}

struct AddProfileLoader: View {
    var body: some View {
        ProgressView()
    }
}

struct AddProfilesErrorMessage: View {
    var message: String

    var body: some View {
        Text(message)
            .font(.title)
    }
}
