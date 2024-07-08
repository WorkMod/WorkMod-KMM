//
//  Splash.swift
//  iosApp
//
//  Created by Riyas Edathadathil on 03/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared


extension SplashView {
    
    @MainActor
    class SplashViewModelWrapper: ObservableObject {
        
        let mainViewModel: MainViewModel
        //var onLoginStatus: (loginStatus: Bool)  -> Void
        var onLoginStatus: (Bool) -> Void
        
        init() {
            mainViewModel = MainInjector().mainViewModel
            onLoginStatus = {_ in}
        }
        
        func checkLoginStatus() {
            Task {
                do {
                    let seconds = 2.0
                    try await Task.sleep(nanoseconds: UInt64(seconds * Double(NSEC_PER_SEC)))
                    let isLoggedIn = try await mainViewModel.isLoggedIn()
                    self.onLoginStatus(isLoggedIn.boolValue)
                    print("checkLoginStatus isloggedIn: \(isLoggedIn.boolValue)")
                } catch _ {
                    print("error: checkLoginStatus")
                    onLoginStatus(false)
                }
            }
        }
    
    }
}

struct SplashView: View {
    
    @ObservedObject private(set) var viewModel: SplashViewModelWrapper
    
    init(viewModel: SplashViewModelWrapper, onLoginStatus: @escaping (Bool) -> Void) {
        self.viewModel = viewModel
        self.viewModel.onLoginStatus = onLoginStatus
    }
    
    var body: some View {
        ZStack {
            Color.blue
                .ignoresSafeArea()
            Text("WorkMod !")
                .font(.system(size: 54, weight: .thin, design: .rounded))
                //.frame(maxWidth: .infinity, maxHeight: 60)
                .foregroundColor(Color.white)
                //.background(Color.blue)
                //.cornerRadius(10)
        }.onAppear {
            viewModel.checkLoginStatus()
        }
        
    }
}

#Preview {
    SplashView(viewModel: .init(), onLoginStatus: {_ in })
}
