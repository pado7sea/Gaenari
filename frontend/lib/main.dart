import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:forsythia/provider/footer_provider.dart';
import 'package:forsythia/provider/login_info_provider.dart';
import 'package:forsythia/provider/signup_provider.dart';
import 'package:forsythia/screens/dashboard/dashboard_screen.dart';
import 'package:forsythia/screens/doghouse/doghouse_screen.dart';
import 'package:forsythia/screens/login/login_screen.dart';
import 'package:forsythia/screens/login/welcome_screen.dart';
import 'package:forsythia/screens/program/program_screen.dart';
import 'package:forsythia/screens/record/record_screen.dart';
import 'package:forsythia/screens/setting/setting_screen.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/widgets/footer.dart';
import 'package:go_router/go_router.dart';
import 'package:provider/provider.dart';
import 'package:flutter/services.dart';

void main() async {
  // debugPaintSizeEnabled = true;
  WidgetsFlutterBinding.ensureInitialized(); // Flutter 엔진과 위젯 트리 바인딩
  // await Firebase.initializeApp(
  //   options: DefaultFirebaseOptions.currentPlatform,
  // );
  // await dotenv.load(fileName: ".env"); // .env 파일 로드

  // 로그인 정보 가져오기
  final SecureStorageService secureStorageService = SecureStorageService();
  final loginInfo = await secureStorageService.getLoginInfo();

  // 초기 위치 설정
  String initialLocation = loginInfo != null ? '/home' : '/welcome';

  runApp(MyApp(initialLocation: initialLocation));
}

class MyApp extends StatefulWidget {
  final String initialLocation;

  const MyApp({required this.initialLocation, super.key});

  @override
  // ignore: no_logic_in_create_state
  State<MyApp> createState() => _MyAppState(initialLocation: initialLocation);
}

class _MyAppState extends State<MyApp> {
  late final GoRouter _router;
  final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();
  final String initialLocation;

  _MyAppState({required this.initialLocation});

  @override
  void initState() {
    super.initState();
    _router = GoRouter(
      debugLogDiagnostics: true,
      initialLocation: initialLocation, // 초기 위치 설정
      navigatorKey: navigatorKey,
      routes: [
        GoRoute(
          path: '/welcome',
          builder: (context, state) => const WelcomeScreen(),
        ),
        GoRoute(
          path: '/login',
          builder: (context, state) => const LoginScreen(),
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
    SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]);
    return MultiProvider(
        providers: [
          ChangeNotifierProvider(create: (context) => FooterProvider()),
          ChangeNotifierProvider(create: (context) => SignupProvider()),
          ChangeNotifierProvider(create: (context) => LoginInfoProvider()),
        ],
        child: MaterialApp.router(
          routerDelegate: _router.routerDelegate,
          routeInformationParser: _router.routeInformationParser,
          routeInformationProvider: _router.routeInformationProvider,
          title: '개나리',
          localizationsDelegates: const [
            GlobalMaterialLocalizations.delegate,
            GlobalCupertinoLocalizations.delegate,
          ],
          supportedLocales: const [
            // Locale('en', ''), // 이거 없어야 한글날짜 나옴
            Locale('ko', ''), // Korean, no country code
          ],
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
          ),
          debugShowCheckedModeBanner: false,
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
    RecordScreen(),
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
          _onItemTapped(index);
        },
      ),
    );
  }
}
