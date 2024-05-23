import 'package:forsythia/firebase_options.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
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
import 'package:permission_handler/permission_handler.dart';
import 'package:provider/provider.dart';
import 'package:flutter/services.dart';

@pragma('vm:entry-point')
Future<void> _firebaseMessagingBackgroundHandler(RemoteMessage message) async {
  await Firebase.initializeApp();
}

@pragma('vm:entry-point')
void backgroundHandler(NotificationResponse details) {}

void initializeNotification() async {
  final flutterLocalNotificationsPlugin = FlutterLocalNotificationsPlugin();

  await flutterLocalNotificationsPlugin
      .resolvePlatformSpecificImplementation<
          AndroidFlutterLocalNotificationsPlugin>()
      ?.createNotificationChannel(const AndroidNotificationChannel(
          'high_importance_channel', 'high_importance_notification',
          importance: Importance.max));

  await flutterLocalNotificationsPlugin.initialize(
    const InitializationSettings(
      android: AndroidInitializationSettings("@mipmap/ic_launcher"),
      iOS: DarwinInitializationSettings(),
    ),
    onDidReceiveNotificationResponse: (details) {},
    onDidReceiveBackgroundNotificationResponse: backgroundHandler,
  );

  await FirebaseMessaging.instance.setForegroundNotificationPresentationOptions(
    alert: true,
    badge: true,
    sound: true,
  );

  FirebaseMessaging.onMessage.listen((RemoteMessage message) async {
    RemoteNotification? notification = message.notification;

    if (notification != null) {
      flutterLocalNotificationsPlugin.show(
          notification.hashCode,
          notification.title,
          notification.body,
          const NotificationDetails(
            android: AndroidNotificationDetails(
              'high_importance_channel',
              'high_importance_notification',
              importance: Importance.max,
            ),
            iOS: DarwinNotificationDetails(),
          ),
          payload: message.data['test_paremeter1']);

      print("수신자 측 메시지 수신");
    }
  });

  RemoteMessage? message = await FirebaseMessaging.instance.getInitialMessage();

  if (message != null) {}
}

void main() async {
  // debugPaintSizeEnabled = true;
  WidgetsFlutterBinding.ensureInitialized(); // Flutter 엔진과 위젯 트리 바인딩
  await Firebase.initializeApp();
  FirebaseMessaging.onBackgroundMessage(_firebaseMessagingBackgroundHandler);
  initializeNotification();

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
    _requestNotificationPermissions(); // 알림 권한 요청
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

  void _requestNotificationPermissions() async {
    await Permission.notification.request();
    //알림 권한 요청
    // if ((status.isDenied || status.isPermanentlyDenied) && mounted) {
    //   WidgetsBinding.instance.addPostFrameCallback((_) {
    //     showDialog(
    //       // 알림 권한이 거부되었을 경우 다이얼로그 출력
    //       context: context,
    //       builder: (context) => AlertDialog(
    //         title: Text('알림 권한이 거부되었습니다.'),
    //         content: Text('알림을 받으려면 앱 설정에서 권한을 허용해야 합니다.'),
    //         actions: <Widget>[
    //           TextButton(
    //             child: Text('설정'), //다이얼로그 버튼의 죄측 텍스트
    //             onPressed: () {
    //               Navigator.of(context).pop();
    //               openAppSettings(); //설정 클릭시 권한설정 화면으로 이동
    //             },
    //           ),
    //           TextButton(
    //             child: Text('취소'), //다이얼로그 버튼의 우측 텍스트
    //             onPressed: () => Navigator.of(context).pop(), //다이얼로그 닫기
    //           ),
    //         ],
    //       ),
    //     );
    //   });
    // }
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
  // ignore: library_private_types_in_public_api
  _MainNavigationState createState() => _MainNavigationState();
}

class _MainNavigationState extends State<MainNavigation> {
  int _selectedIndex = 0;

  final List<Widget> _widgetOptions = [
    const DashBoardScreen(),
    const RecordScreen(),
    const ProgramScreen(),
    const DogHouseScreen(),
    const SettingScreen(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _widgetOptions[_selectedIndex], // 선택된 인덱스에 따라 해당 페이지 위젯을 표시
      bottomNavigationBar: CustomBottomNavigationBar(
        selectedIndex: _selectedIndex,
        onItemSelected: (index) {
          _onItemTapped(index);
        },
      ),
    );
  }
}
