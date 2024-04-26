import 'package:flutter/material.dart';
import 'package:forsythia/provider/footer_provider.dart';
import 'package:forsythia/screens/dashboard/dashboard_screen.dart';
import 'package:forsythia/screens/doghouse/doghouse_screen.dart';
import 'package:forsythia/screens/program/program_screen.dart';
import 'package:forsythia/screens/record/record_screen.dart';
import 'package:forsythia/screens/setting/setting_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/widgets/footer.dart';
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
