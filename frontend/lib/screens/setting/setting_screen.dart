import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:forsythia/screens/coin/coin_screen.dart';
import 'package:forsythia/screens/setting/edit_bodyinfo_screen.dart';
import 'package:forsythia/screens/setting/edit_nickname_screen.dart';
import 'package:forsythia/screens/setting/edit_password_screen.dart';
import 'package:forsythia/screens/setting/notification_screen.dart';
import 'package:forsythia/service/fcm_service.dart';
import 'package:forsythia/service/secure_storage_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/button_widgets.dart';
import 'package:forsythia/widgets/modal.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:go_router/go_router.dart';

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
            height: 10,
          ),
          _nickname(),
          _password(),
          _body(),
          // _notification(),
          _coinHistory(),
          _logout(context),
        ],
      ),
    );
  }

  Widget _nickname() {
    return GestureDetector(
      onTap: () {
        Navigator.of(context).push(SlidePageRoute(nextPage: EditNickName()));
      },
      child: Container(
        decoration: myBoxDecoration,
        width: double.infinity,
        padding: EdgeInsets.all(20),
        margin: EdgeInsets.fromLTRB(20, 10, 20, 0),
        child: Row(
          children: const [
            Image(
              image: AssetImage('assets/emoji/smilepace.png'),
              width: 20,
              height: 20,
              fit: BoxFit.cover,
            ),
            Text16(text: '  닉네임변경')
          ],
        ),
      ),
    );
  }

  Widget _password() {
    return GestureDetector(
      onTap: () {
        Navigator.of(context).push(SlidePageRoute(nextPage: EditPassword()));
      },
      child: Container(
        decoration: myBoxDecoration,
        width: double.infinity,
        padding: EdgeInsets.all(20),
        margin: EdgeInsets.fromLTRB(20, 16, 20, 0),
        child: Row(
          children: const [
            Image(
              image: AssetImage('assets/emoji/key.png'),
              width: 20,
              height: 20,
              fit: BoxFit.cover,
            ),
            Text16(text: '  비밀번호변경')
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

  Widget _coinHistory() {
    return GestureDetector(
      onTap: () {
        Navigator.of(context).push(SlidePageRoute(nextPage: CoinScreen()));
      },
      child: Container(
        decoration: myBoxDecoration,
        width: double.infinity,
        padding: EdgeInsets.all(20),
        margin: EdgeInsets.fromLTRB(20, 16, 20, 0),
        child: Row(
          children: const [
            Image(
              image: AssetImage('assets/emoji/money.png'),
              width: 20,
              height: 20,
              fit: BoxFit.cover,
            ),
            Text16(text: '  코인내역')
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

  Widget _logout(context) {
    final SecureStorageService secureStorageService = SecureStorageService();
    return Expanded(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.end,
        children: [
          Text16(
            text: 'v 1.0.5',
            textColor: myGrey,
            bold: true,
          ),
          SizedBox(height: 10),
          GestureDetector(
            onTap: () {
              showModalBottomSheet(
                context: context,
                builder: (BuildContext context) {
                  return BackdropFilter(
                    filter: ImageFilter.blur(sigmaX: 2, sigmaY: 2), // 블러 효과 설정
                    child: ModalContent(
                      height: 250,
                      customWidget: Column(
                        children: [
                          SizedBox(height: 20),
                          // Text20(
                          //   text: '진짜로 정말로',
                          //   bold: true,
                          //   textColor: myRed,
                          // ),
                          Text20(text: '로그아웃 하시겠습니까?', bold: true),
                          SizedBox(height: 30),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              SmallButton(
                                onPressed: () {
                                  Navigator.of(context).pop();
                                },
                                text: "아니오",
                                active: true,
                                widthPadding: 35,
                              ),
                              SizedBox(
                                width: 16,
                              ),
                              SmallButton(
                                onPressed: () async {
                                  await FcmService().fetchFcmDelete();
                                  // 1. 로컬 스토리지에서 로그인 정보 삭제
                                  await secureStorageService.deleteToken();

                                  // 2. 로그인 화면으로 이동
                                  // ignore: use_build_context_synchronously
                                  GoRouter.of(context).go('/welcome');
                                },
                                text: "로그아웃",
                                active: false,
                                widthPadding: 35,
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  );
                },
              );
            },
            child: Text(
              '로그아웃',
              style: TextStyle(
                color: myGrey,
                decoration: TextDecoration.underline,
                decorationColor: myGrey,
                decorationThickness: 2,
              ),
            ),
          ),
          SizedBox(height: 20),
        ],
      ),
    );
  }
}
