import 'package:flutter/material.dart';
import 'package:forsythia/screens/login/login_screen.dart';
import 'package:forsythia/screens/signup/signup_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';

class WelcomeScreen extends StatefulWidget {
  const WelcomeScreen({super.key});

  @override
  State<WelcomeScreen> createState() => LoginScreenState();
}

class LoginScreenState extends State<WelcomeScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Image(
            image: AssetImage('assets/images/logo.png'),
            width: 200,
            height: 50,
            fit: BoxFit.cover,
          ),
          SizedBox(
            height: 50,
          ),
          ElevatedButton(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => LoginScreen()),
              );
            },
            style: ElevatedButton.styleFrom(
              backgroundColor: myYellow,
              elevation: 0,
              minimumSize: Size(MediaQuery.of(context).size.width - 200, 40),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            child: Text20(
              text: '로그인',
              textColor: Colors.white,
              bold: true,
            ),
          ),
          SizedBox(
            height: 20,
          ),
          GestureDetector(
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => SignupScreen()),
              );
            },
            child: Text(
              '회원가입',
              style: TextStyle(
                  color: myGrey,
                  decoration: TextDecoration.underline,
                  decorationColor: myGrey,
                  decorationThickness: 2),
            ),
          ),
        ],
      ),
    ));
  }
}
