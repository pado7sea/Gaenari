import 'package:flutter/material.dart';
import 'package:forsythia/provider/signup_provider.dart';
import 'package:forsythia/screens/login/login_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:provider/provider.dart';

class SignupEndScreen extends StatefulWidget {
  const SignupEndScreen({super.key});

  @override
  State<SignupEndScreen> createState() => _SignupEndScreenState();
}

class _SignupEndScreenState extends State<SignupEndScreen> {
  @override
  Widget build(BuildContext context) {
    var nickName = Provider.of<SignupProvider>(context).user.nickName;

    var maintext = Center(
      child: RichText(
        textAlign: TextAlign.center,
        text: TextSpan(
          children: [
            TextSpan(
              text: '회원가입 완료  ',
              style: TextStyle(
                  color: myMainGreen,
                  fontSize: 26,
                  fontWeight: FontWeight.bold,
                  fontFamily: 'TheJamsil'),
            ),
            WidgetSpan(
              child: Image(
                image: AssetImage('assets/emoji/party.png'),
                width: 25,
                height: 25,
                fit: BoxFit.cover,
              ),
            ),
            TextSpan(
              text: '\n$nickName님에게\n딱맞는 강아지를\n찾았어요~!',
              style: TextStyle(
                  color: myBlack,
                  fontSize: 26,
                  fontWeight: FontWeight.bold,
                  height: 1.5,
                  fontFamily: 'TheJamsil'),
            ),
          ],
        ),
      ),
    );

    return Scaffold(
      appBar: SmallAppBar(
        title: '회원가입',
        back: true,
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          maintext,
          SizedBox(
            height: 70,
          ),
          _button()
        ],
      ),
    );
  }

  // 완료 버튼
  Widget _button() {
    return Padding(
      padding: const EdgeInsets.only(bottom: 20),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          ElevatedButton(
            onPressed: () {
              Navigator.of(context)
                  .push(SlidePageRoute(nextPage: LoginScreen()));
            },
            style: ElevatedButton.styleFrom(
              backgroundColor: myLightGreen,
              elevation: 0,
              minimumSize: Size(MediaQuery.of(context).size.width - 100, 50),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            child: Text20(
              text: '로그인하러 가기',
              textColor: Colors.white,
              bold: true,
            ),
          ),
        ],
      ),
    );
  }
}
