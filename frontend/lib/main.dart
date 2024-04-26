// import 'package:flutter/material.dart';

// import 'package:forsythia/screens/homescreen.dart';
// import 'package:forsythia/theme/color.dart';

// void main() async {
//   WidgetsFlutterBinding.ensureInitialized(); // Flutter 엔진과 위젯 트리 바인딩
//   runApp(MyApp());
// }

// class MyApp extends StatefulWidget {
//   @override
//   State<MyApp> createState() => _MyAppState();
// }

// class _MyAppState extends State<MyApp> {

//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       debugShowCheckedModeBanner: false,
//       theme: ThemeData(scaffoldBackgroundColor: myBackground),
//       home: const HomeScreen(),
//     );
//   }
// }

// import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:forsythia/provider/footer_provider.dart';
import 'package:forsythia/screens/dashboard/dashboard_screen.dart';
import 'package:forsythia/screens/doghouse/doghouse_screen.dart';
// import 'package:forsythia/screens/homescreen.dart';
import 'package:forsythia/screens/program/program_screen.dart';
import 'package:forsythia/screens/record/record_screen.dart';
import 'package:forsythia/screens/setting/setting_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/widgets/footer.dart';
// import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:provider/provider.dart';
// import 'package:stad/constant/animated_indexed_stack.dart';
// import 'package:stad/firebase_options.dart';
// import 'package:stad/providers/user_provider.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized(); // Flutter 엔진과 위젯 트리 바인딩
  // await Firebase.initializeApp(
  //   options: DefaultFirebaseOptions.currentPlatform,
  // );
  // await dotenv.load(fileName: ".env"); // .env 파일 로드

  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  int _selectedIndex = 0;

  static const List<Widget> _widgetOptions = <Widget>[
    DashBoardScreen(),
    RecodScreen(),
    ProgramScreen(),
    DogHouseScreen(),
    SettingScreen(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => FooterProvider()),
      ],
      child: MaterialApp(
        title: '개나리',
        theme: ThemeData(
          scaffoldBackgroundColor: myBackground,
          splashColor: Colors.transparent,
          highlightColor: Colors.transparent,
          // visualDensity: VisualDensity.adaptivePlatformDensity,
          // pageTransitionsTheme: PageTransitionsTheme(
          //   builders: {
          //     TargetPlatform.android: CupertinoPageTransitionsBuilder(),
          //     TargetPlatform.iOS: CupertinoPageTransitionsBuilder(),
          //   },
          // ),
          // fontFamily: 'MainFont',
        ),
        debugShowCheckedModeBanner: false,
        home: Scaffold(
          body: AnimatedIndexedStack(
            index: _selectedIndex,
            children: _widgetOptions,
          ),
          bottomNavigationBar: CustomBottomNavigationBar(
            selectedIndex: _selectedIndex,
            onItemSelected: _onItemTapped,
          ),
        ),
      ),
    );
  }
}
