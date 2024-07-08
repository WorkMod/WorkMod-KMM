//
//  SignUpView.swift
//  iosApp
//
//  Created by Riyas Edathadathil on 06/07/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension SignUpView {
    
    @MainActor
    class AuthViewModelWrapper: ObservableObject {
        let authViewModel: AuthViewModel
        //var onSignIn: (() -> Void)?
        var onSignUp: () -> Void = {}
        
        
        init() {
            authViewModel = AuthInjector().authViewModel
            signUpState = authViewModel.signUpResult.value
        }
        
        @Published var signUpState: BoolState
        
        func startObserving() {
            Task {
                for await newState in authViewModel.signUpResult {
                    print("new signUpResult state!: \(newState.success)")
                    self.signUpState = newState
                    if (newState.success) {
                        self.authViewModel.signUpReset()
                        self.onSignUp()
                    }
                }
            }
        }
    }
}

struct SignUpView: View {
    
    @ObservedObject private(set) var viewModel: AuthViewModelWrapper
    var onSignInClick: () -> Void = {}
    
    init(viewModel: AuthViewModelWrapper, onSignUp: @escaping () -> Void, onSignInClick: @escaping () -> Void) {
        self.viewModel = viewModel
        self.viewModel.onSignUp = onSignUp
        self.onSignInClick = onSignInClick
    }
    
    
    @State private var name: String = "User One"
    @State private var email: String = "essie@gmail.com"
    @State private var password: String = "123456"
    
    var body: some View {
        ZStack {
            Color.blue
                .ignoresSafeArea()
            Circle()
                .scale(1.7)
                .foregroundColor(.white.opacity(0.15))
            Circle()
                .scale(1.35)
                .foregroundColor(.white)
            
            
            VStack {
                Spacer()
                VStack {
                    TextField(
                        "Name",
                        text: $name
                    )
                    .autocapitalization(.words)
                    .disableAutocorrection(true)
                    .padding(.top, 20)
                    
                    Divider()
                    TextField(
                        "Email",
                        text: $email
                    )
                    .autocapitalization(.none)
                    .disableAutocorrection(true)
                    .padding(.top, 20)
                    
                    Divider()
                    
                    SecureField(
                        "Password",
                        text: $password
                    )
                    .padding(.top, 20)
                    
                    Divider()
                }
                
                Spacer()
                if (viewModel.signUpState.success) {
                    Text("Success")
                    //self.onSignIn
                } else if (!viewModel.signUpState.error.isEmpty){
                    Text("Error: \(viewModel.signUpState.error)")
                    let _ = print("error: \(viewModel.signUpState.error)")
                }
                Button(
                    action: { viewModel.authViewModel.signUp(name: name, email: email, password: password) },
                    label: {
                        Text("SignUp")
                            .font(.system(size: 24, weight: .bold, design: .default))
                            .frame(maxWidth: .infinity, maxHeight: 60)
                            .foregroundColor(Color.white)
                            .background(Color.blue)
                            .cornerRadius(10)
                    }
                )
                
                Button(
                    action: { self.onSignInClick() },
                    label: {
                        Text("Sign In")
                            .font(.system(size: 20, weight: .regular, design: .default))
                            .frame(maxWidth: .infinity, maxHeight: 40)
                            .foregroundColor(Color.white)
                            .background(Color.blue)
                            .cornerRadius(10)
                    }
                )
            }
            .padding(30)
            
            if viewModel.signUpState.loading {
                SignUpLoader()
            }
            
            
        }.onAppear {
            self.viewModel.startObserving()
        }
        
    }
}


struct SignUpLoader: View {
    var body: some View {
        ProgressView()
    }
}

struct SignUpErrorMessage: View {
    var message: String
    
    var body: some View {
        Text(message)
            .font(.title)
    }
}


//#Preview {
//    SignUpView()
//}
