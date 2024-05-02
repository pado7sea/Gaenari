import 'package:flutter/material.dart';
import 'package:forsythia/screens/login/login_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/SlidePageRoute.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class signupEndScreen extends StatefulWidget {
  const signupEndScreen({super.key});

  @override
  State<signupEndScreen> createState() => _signupEndScreenState();
}

class _signupEndScreenState extends State<signupEndScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(
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

  var maintext = Center(
    child: RichText(
      textAlign: TextAlign.center,
      text: TextSpan(
        children: const [
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
          )),
          TextSpan(
            text: '\n이재신님에게\n딱맞는 강아지를\n찾았어요~!',
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
                  .push(SlidePageRoute(nextPage: loginScreen()));
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
