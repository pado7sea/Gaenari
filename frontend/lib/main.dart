import 'package:flutter/material.dart';
import 'package:forsythia/provider/footer_provider.dart';
import 'package:forsythia/screens/dashboard/dashboard_screen.dart';
import 'package:forsythia/screens/doghouse/doghouse_screen.dart';
import 'package:forsythia/screens/login/login_screen.dart';
import 'package:forsythia/screens/login/welcome_screen.dart';
import 'package:forsythia/screens/program/program_screen.dart';
import 'package:forsythia/screens/record/record_screen.dart';
import 'package:forsythia/screens/setting/setting_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/widgets/footer.dart';
import 'package:go_router/go_router.dart';
import 'package:provider/provider.dart';

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
  late final GoRouter _router;
  final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

  // int _selectedIndex = 0;
  // bool _loginactive = false;

  // static const List<Widget> _widgetOptions = <Widget>[
  //   DashBoardScreen(),
  //   RecodScreen(),
  //   ProgramScreen(),
  //   DogHouseScreen(),
  //   SettingScreen(),
  // ];

  // void _onItemTapped(int index) {
  //   setState(() {
  //     _selectedIndex = index;
  //   });
  // }

  @override
  void initState() {
    super.initState();
    _router = GoRouter(
      debugLogDiagnostics: true,
      initialLocation: '/welcome',
      navigatorKey: navigatorKey,
      routes: [
        GoRoute(
          path: '/welcome',
          builder: (context, state) => const welcomeScreen(),
        ),
        GoRoute(
          path: '/login',
          builder: (context, state) => const loginScreen(),
        ),
        GoRoute(
          path: '/home',
          builder: (context, state) => const MainNavigation(),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
        providers: [
          ChangeNotifierProvider(create: (context) => FooterProvider()),
        ],
        child: MaterialApp.router(
          routerDelegate: _router.routerDelegate,
          routeInformationParser: _router.routeInformationParser,
          routeInformationProvider: _router.routeInformationProvider,
          title: '개나리',
          theme: ThemeData(
            fontFamily: 'TheJamsil', // 사용할 폰트 패밀리 지정
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

          // home: _loginactive
          //     ? Scaffold(
          //         body: AnimatedIndexedStack(
          //           index: _selectedIndex,
          //           children: _widgetOptions,
          //         ),
          //         bottomNavigationBar: CustomBottomNavigationBar(
          //           selectedIndex: _selectedIndex,
          //           onItemSelected: _onItemTapped,
          //         ),
          //       )
          //     : Scaffold(
          //         body: wellcomeScreen(),
          //       )),
        ));
  }
}

class MainNavigation extends StatefulWidget {
  const MainNavigation({super.key});

  @override
  State<MainNavigation> createState() => _MainNavigationState();
}

class _MainNavigationState extends State<MainNavigation> {
  int _selectedIndex = 0;

  final List<Widget> _widgetOptions = [
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
    return Scaffold(
      body: AnimatedIndexedStack(
        index: _selectedIndex,
        children: _widgetOptions,
      ),
      bottomNavigationBar: CustomBottomNavigationBar(
        selectedIndex: _selectedIndex,
        onItemSelected: (index) {
          setState(() {
            _selectedIndex = index;
          });
        },
      ),
    );
  }
}
