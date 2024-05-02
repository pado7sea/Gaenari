import 'package:flutter/material.dart';
import 'package:forsythia/screens/setting/account_screen.dart';
import 'package:forsythia/screens/setting/bodyinfo_screen.dart';
import 'package:forsythia/screens/setting/notification_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class SettingScreen extends StatefulWidget {
  const SettingScreen({super.key});

  @override
  State<SettingScreen> createState() => _SettingScreenState();
}

class _SettingScreenState extends State<SettingScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(title: '설정'),
      body: Column(
        children: [
          SizedBox(
            height: 30,
          ),
          _account(),
          _notification(),
          _body(),
          _logout(),
        ],
      ),
    );
  }

  Widget _account() {
    return GestureDetector(
      onTap: () {
        Navigator.of(context).push(SlidePageRoute(nextPage: AccountScreen()));
      },
      child: Container(
        decoration: myBoxDecoration,
        width: double.infinity,
        padding: EdgeInsets.all(20),
        margin: EdgeInsets.fromLTRB(20, 16, 20, 0),
        child: Row(
          children: const [
            Image(
              image: AssetImage('assets/emoji/smilepace.png'),
              width: 20,
              height: 20,
              fit: BoxFit.cover,
            ),
            Text16(text: '  계정')
          ],
        ),
      ),
    );
  }

  Widget _notification() {
    return GestureDetector(
      onTap: () {
        Navigator.of(context)
            .push(SlidePageRoute(nextPage: NotificationScreen()));
      },
      child: Container(
        decoration: myBoxDecoration,
        width: double.infinity,
        padding: EdgeInsets.all(20),
        margin: EdgeInsets.fromLTRB(20, 16, 20, 0),
        child: Row(
          children: const [
            Image(
              image: AssetImage('assets/emoji/bell.png'),
              width: 20,
              height: 20,
              fit: BoxFit.cover,
            ),
            Text16(text: '  알림')
          ],
        ),
      ),
    );
  }

  Widget _body() {
    return GestureDetector(
      onTap: () {
        Navigator.of(context).push(SlidePageRoute(nextPage: BodyinfoScreen()));
      },
      child: Container(
        decoration: myBoxDecoration,
        width: double.infinity,
        padding: EdgeInsets.all(20),
        margin: EdgeInsets.fromLTRB(20, 16, 20, 0),
        child: Row(
          children: const [
            Image(
              image: AssetImage('assets/emoji/ruler.png'),
              width: 20,
              height: 20,
              fit: BoxFit.cover,
            ),
            Text16(text: '  신체정보')
          ],
        ),
      ),
    );
  }

  Widget _logout() {
    return Expanded(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          Text16(
            text: 'v 1.0.0',
            textColor: myGrey,
            bold: true,
          ),
          SizedBox(height: 10),
          GestureDetector(
            onTap: () {
              // 로그아웃 로직 추가
            },
            child: Text(
              '로그아웃',
              style: TextStyle(
                  color: myGrey,
                  decoration: TextDecoration.underline,
                  decorationColor: myGrey,
                  decorationThickness: 2),
            ),
          ),
          SizedBox(
            height: 20,
          ),
        ],
      ),
    );
  }
}
