//
//  SignInView.swift
//  iosApp
//
//  Created by Riyas Edathadathil on 21/06/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

//
//  SignInView.swift
//  iosApp
//
//  Created by Riyas Edathadathil on 21/06/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension SignInView {
    
    @MainActor
    class AuthViewModelWrapper: ObservableObject {
        let authViewModel: AuthViewModel
        //var onSignIn: (() -> Void)?
        var onSignIn: () -> Void = {}
        
        
        init() {
            authViewModel = AuthInjector().authViewModel
            signInState = authViewModel.signInResult.value
        }
        
        @Published var signInState: BoolState
        
        func startObserving() {
            Task {
                for await newState in authViewModel.signInResult {
                    print("new signInResult state! : \(newState.success)")
                    self.signInState = newState
                    if (newState.success) {
                        self.authViewModel.signInReset()
                        self.onSignIn()
                    }
                }
            }
        }
    }
}

struct SignInView: View {
    
    @ObservedObject private(set) var viewModel: AuthViewModelWrapper
    var onSignUpClick: () -> Void = {}
    
    init(viewModel: AuthViewModelWrapper, onSignIn: @escaping () -> Void, onSignUpClick: @escaping () -> Void) {
        self.viewModel = viewModel
        self.viewModel.onSignIn = onSignIn
        self.onSignUpClick = onSignUpClick
    }
    
    
    @State private var email: String = "riyas@gmail.com"
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
                if (viewModel.signInState.success) {
                    Text("Success")
                    //self.onSignIn
                } else if (!viewModel.signInState.error.isEmpty){
                    Text("Error: \(viewModel.signInState.error)")
                    let _ = print("error: \(viewModel.signInState.error)")
                }
                Button(
                    action: { viewModel.authViewModel.signIn(email: email, password: password) },
                    label: {
                        Text("Login")
                            .font(.system(size: 24, weight: .bold, design: .default))
                            .frame(maxWidth: .infinity, maxHeight: 50)
                            .foregroundColor(Color.white)
                            .background(Color.blue)
                            .cornerRadius(10)
                    }
                )
                
                Button(
                    action: { self.onSignUpClick() },
                    label: {
                        Text("Sign Up")
                            .font(.system(size: 20, weight: .regular, design: .default))
                            .frame(maxWidth: .infinity, maxHeight: 40)
                            .foregroundColor(Color.white)
                            .background(Color.blue)
                            .cornerRadius(10)
                    }
                )
            }
            
            .padding(30)
            
            if viewModel.signInState.loading {
                SignInLoader()
            }
            
            
        }.onAppear {
            self.viewModel.startObserving()
        }
        
    }
}


struct SignInLoader: View {
    var body: some View {
        ProgressView()
    }
}

struct SignInErrorMessage: View {
    var message: String
    
    var body: some View {
        Text(message)
            .font(.title)
    }
}

//struct SignInView_Previews: PreviewProvider {
//    static var previews: some View {
//        SignInView()
//    }
//}
//
//#Preview {
//    SignInView()
//}
