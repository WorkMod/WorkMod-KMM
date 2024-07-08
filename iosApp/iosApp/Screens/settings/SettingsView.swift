//
//  SettingsView.swift
//  iosApp
//
//  Created by Riyas Edathadathil on 05/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension SettingsView {
    
    @MainActor
    class SettingsViewModelWrapper: ObservableObject {
        
        let mainViewModel: MainViewModel
        var onSignOut: () -> Void
        
        init() {
            self.mainViewModel = MainInjector().mainViewModel
            self.onSignOut = {}
            self.signOutState = mainViewModel.signOutResult.value
            self.userName = ""
        }
        
        @Published var signOutState: BoolState
        @Published var userName: String
        
        func startObserving() {
            Task {
                for await newState in mainViewModel.signOutResult {
                    print("new signOutResult state!: \(newState.success)")
                    self.signOutState = newState
                    if (newState.success) {
                        self.mainViewModel.signOutReset()
                        self.onSignOut()
                    }
                }
            }
            Task {
                for await newName in mainViewModel.getUserName() {
                    print("UserName: \(newName)")
                    self.userName = newName
                }
                
            }
        }
    
    }
}

struct SettingsView: View {
    
    @ObservedObject private(set) var viewModel: SettingsViewModelWrapper
    
    init(viewModel: SettingsViewModelWrapper, onSignOut: @escaping () -> Void) {
        self.viewModel = viewModel
        self.viewModel.onSignOut = onSignOut
    }
    
    var body: some View {
        ZStack {
            Color.white
                .ignoresSafeArea()
            VStack {
                
                Text(viewModel.userName)
                    .font(.system(size: 20, weight: .bold, design: .default))
                Divider()
                Button(
                    action: { viewModel.mainViewModel.signOut() },
                    label: {
                        Text("Logout")
                            .font(.system(size: 24, weight: .bold, design: .default))
                            .frame(maxWidth: .infinity, maxHeight: 60)
                            .foregroundColor(Color.white)
                            .background(Color.blue)
                            .cornerRadius(10)
                    }
                )
            }
            
        }.onAppear {
            viewModel.startObserving()
        }
        
    }
}

//#Preview {
//    SettingsView(viewModel: .init(), onLoginStatus: {})
//}
