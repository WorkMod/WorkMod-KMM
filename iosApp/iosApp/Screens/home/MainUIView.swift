//
//  MainUIView.swift
//  iosApp
//
//  Created by Riyas Edathadathil on 21/06/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct MainUIView: View {
    
    @State private var selectedIndex: Int = 0
    var onSignOut: () -> Void
    
    init(onSignOut: @escaping () -> Void) {
        self.onSignOut = onSignOut
    }
    
    var body: some View {
        TabView(selection: $selectedIndex) {
                    NavigationStack() {
                        Text("Profiles")
                            .navigationTitle("Profiles")
                    }
                    .tabItem {
                        Text("Profiles")
                        Image(systemName: "house.fill")
                            .renderingMode(.template)
                    }
                    .tag(0)
                    
                    NavigationStack() {
                        Text("Jobs")
                            .navigationTitle("Jobs")
                    }
                    .tabItem {
                        Label("Jobs", systemImage: "person.fill")
                    }
                    .tag(1)
                    
                    NavigationStack() {
                        SettingsView(viewModel: .init(), onSignOut: self.onSignOut)
                    }
                    .tabItem {
                        Text("Settings")
                        Image(systemName: "info.circle")
                    }
                    .badge("12")
                    .tag(2)
                }
                //1
                .tint(.pink)
                .onAppear(perform: {
                    //2
                    UITabBar.appearance().unselectedItemTintColor = .systemBrown
                    //3
                    UITabBarItem.appearance().badgeColor = .systemPink
                    //4
                    UITabBar.appearance().backgroundColor = .systemGray4.withAlphaComponent(0.4)
                    //5
                    UINavigationBar.appearance().largeTitleTextAttributes = [.foregroundColor: UIColor.systemPink]
                    //UITabBar.appearance().scrollEdgeAppearance = UITabBarAppearance()
                    //Above API will kind of override other behaviour and bring the default UI for TabView
                })
    }
}

//#Preview {
//    MainUIView()
//}
