import SwiftUI
import shared

enum StartScreen {
    case Splash
    case SignIn
    case SignUp
    case Main
}

struct ContentView: View {
	let greet = Greeting().greet()

    @State private var navPath: NavigationPath = .init()
    @State private var screen: StartScreen = StartScreen.Splash
    
    private let mainView = "mainView"
    private let loginView = "loginView"
    
	var body: some View {
        
        ZStack {
            switch screen {
            case StartScreen.Splash:
                SplashView(viewModel: .init(), onLoginStatus: { isLoggedIn in
                    print("NavStack: isLoggedIn:\(isLoggedIn)")
                    if (isLoggedIn) {
                        screen = StartScreen.Main
                    } else {
                        screen = StartScreen.SignIn
                    }
                })
            case StartScreen.SignIn:
                SignInView(viewModel: .init(), onSignIn: {
                    screen = StartScreen.Main
                }, onSignUpClick: {
                    screen = StartScreen.SignUp
                })
            case StartScreen.SignUp:
                SignUpView(viewModel: .init(), onSignUp: {
                    screen = StartScreen.Main
                }, onSignInClick: {
                    screen = StartScreen.SignIn
                })
            case StartScreen.Main:
                MainUIView(onSignOut: {
                    screen = StartScreen.SignIn
                })
            }
        }
        
//        NavigationStack(path: $navPath) {
//            SplashView(viewModel: .init(), onLoginStatus: { isLoggedIn in
//                print("NavStack: isLoggedIn:\(isLoggedIn)")
//                //navPath.removeLast()
//                if (isLoggedIn) {
//                    navPath.append(mainView)
//                } else {
//                    navPath.append(loginView)
//                }
//            })
//            .navigationDestination(for: String.self) { screen in
//                let _ = print("show \(screen)")
//                switch screen {
//                case loginView:
//                    SignInView(viewModel: .init(), onSignIn: navigateToMainView)
//                case mainView:
//                    MainUIView(onSignOut: navigateToLoginView)
//                default:
//                    Text("Default View!")
//                }
//            }
//        }
	}
    
    func navigateToMainView() {
        navPath.append(mainView)
    }
    func navigateToLoginView() {
        navPath.removeLast(navPath.count)
        navPath.append(loginView)
    }
    
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
