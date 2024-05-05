import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:forsythia/screens/setting/account_screen.dart';
import 'package:forsythia/screens/setting/bodyinfo_screen.dart';
import 'package:forsythia/screens/setting/notification_screen.dart';
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
            height: 30,
          ),
          _account(),
          _notification(),
          _body(),
          _logout(context),
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

  Widget _logout(context) {
    final SecureStorageService secureStorageService = SecureStorageService();
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
              showModalBottomSheet(
                context: context,
                builder: (BuildContext context) {
                  return BackdropFilter(
                    filter: ImageFilter.blur(sigmaX: 3, sigmaY: 3), // 블러 효과 설정
                    child: ModalContent(
                      height: 230,
                      customWidget: Column(
                        children: [
                          SizedBox(height: 10),
                          Text20(
                            text: '진짜로 정말로',
                            bold: true,
                            textColor: myRed,
                          ),
                          Text16(text: '로그아웃 하시겠어요?', bold: true),
                          SizedBox(height: 20),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              SmallButton(
                                onPressed: () {
                                  Navigator.of(context).pop();
                                },
                                text: "잘못누름",
                                active: true,
                                widthPadding: 30,
                              ),
                              SizedBox(
                                width: 16,
                              ),
                              SmallButton(
                                onPressed: () async {
                                  // 1. 로컬 스토리지에서 로그인 정보 삭제
                                  await secureStorageService.deleteToken();

                                  // 2. 로그인 화면으로 이동
                                  GoRouter.of(context).go('/welcome');
                                },
                                text: "로그아웃",
                                active: false,
                                widthPadding: 30,
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
