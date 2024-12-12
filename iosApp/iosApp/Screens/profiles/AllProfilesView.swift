//
//  AllProfilesView.swift
//  iosApp
//
//  Created by Riyas Edathadathil on 15/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension AllProfilesView {
    
    @MainActor
    class AllProfilesViewModelWrapper: ObservableObject {
        
        let profileViewModel: ProfileViewModel
        var onItemClick: (String) -> Void
        
        init() {
            self.profileViewModel = ProfileInjector().profileViewModel
            self.onItemClick = {_ in}
            self.getAllProfilesResult = GetAllProfilesResult(success: false, loading: false, profiles: [], error: "")
        }
        
        @Published var getAllProfilesResult: GetAllProfilesResult
        
        func startObserving() {
            Task {
                for await allProfilesResult in profileViewModel.getAllProfilesResult {
                    print("new getAllProfilesResult: \(allProfilesResult.success)")
                    self.getAllProfilesResult = allProfilesResult
                }
            }
        }
        
        func getAllProfiles() {
            self.profileViewModel.getAllProfiles()
        }
    
    }
}

struct AllProfilesView: View {
    
    @ObservedObject private(set) var viewModel: AllProfilesViewModelWrapper
    
    init(viewModel: AllProfilesViewModelWrapper, onItemClick: @escaping(String) -> Void) {
        self.viewModel = viewModel
        self.viewModel.onItemClick = onItemClick
    }
    
    var body: some View {
        VStack {
            //AllProfilesAppBar()
            
            if viewModel.getAllProfilesResult.loading {
                AllProfilesLoader()
            }
            
            if (!viewModel.getAllProfilesResult.error.isEmpty) {
                AllProfilesErrorMessage(message: viewModel.getAllProfilesResult.error)
            }
            
            if(!viewModel.getAllProfilesResult.profiles.isEmpty) {
                ScrollView {
                    LazyVStack(spacing: 10) {
                        ForEach(viewModel.getAllProfilesResult.profiles, id: \.self) { profile in
                            ProfileItemView(profile: profile, onItemClick: { id in
                                viewModel.onItemClick(id)
                            })
                        }
                    }
                }
            }
            
        }.onAppear{
            self.viewModel.startObserving()
        }.onFirstAppear {
            self.viewModel.getAllProfiles()
        }
    }
}

struct AllProfilesAppBar: View {
    var body: some View {
        Text("Sources")
            .font(.largeTitle)
            .fontWeight(.bold)
    }
}

struct ProfileItemView: View {
    
    var profile: Profile
    var onItemClick: (String) -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {

            Text(profile.title)
                .frame(maxWidth: .infinity, alignment: .leading)
                .font(.title3)
                .fontWeight(.regular)
        }
        .onTapGesture {
            onItemClick(profile.id)
        }
        .padding(16)
    }
}

struct AllProfilesLoader: View {
    var body: some View {
        ProgressView()
    }
}

struct AllProfilesErrorMessage: View {
    var message: String

    var body: some View {
        Text(message)
            .font(.title)
    }
}

//#Preview {
//    AllProfilesView()
//}
