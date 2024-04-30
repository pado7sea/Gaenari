import 'package:flutter/material.dart';
import 'package:forsythia/screens/dashboard/dashboard_screen.dart';
import 'package:forsythia/screens/signup/signup_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';

class loginScreen extends StatefulWidget {
  const loginScreen({super.key});

  @override
  State<loginScreen> createState() => loginScreenState();
}

class loginScreenState extends State<loginScreen> {
  TextEditingController _emailController = TextEditingController();
  TextEditingController _passwordController = TextEditingController();
  String _loginStatus = ''; // 로그인 상태를 나타내는 변수

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
            Padding(
              padding: const EdgeInsets.all(40),
              child: Column(
                children: [
                  Row(
                    children: [
                      Image(
                        image: AssetImage('assets/emoji/pensil.png'),
                        width: 20,
                        height: 20,
                        fit: BoxFit.cover,
                      ),
                      Text16(text: '  이메일')
                    ],
                  ),
                  TextField(
                    controller: _emailController,
                    decoration: InputDecoration(
                        hintText: '이메일을 입력해주세요.',
                        hintStyle: TextStyle(color: Colors.grey),
                        focusedBorder: UnderlineInputBorder(
                            borderSide: BorderSide(color: myBlack))),
                  ),
                  SizedBox(height: 30),
                  Row(
                    children: [
                      Image(
                        image: AssetImage('assets/emoji/pensil.png'),
                        width: 20,
                        height: 20,
                        fit: BoxFit.cover,
                      ),
                      Text16(text: '  비밀번호')
                    ],
                  ),
                  TextField(
                    controller: _passwordController,
                    decoration: InputDecoration(
                        hintText: '비밀번호를 입력해주세요.',
                        hintStyle: TextStyle(color: Colors.grey),
                        focusedBorder: UnderlineInputBorder(
                            borderSide: BorderSide(color: myBlack))),
                  ),
                ],
              ),
            ),
            Text(
              _loginStatus,
              style: TextStyle(
                fontSize: 12,
                color: _loginStatus == '로그인 성공!' ? Colors.green : Colors.red,
              ),
            ),
            ElevatedButton(
              onPressed: () {
                // 여기에 로그인 검사 로직을 추가하세요.
                String email = _emailController.text;
                String password = _passwordController.text;
                // 예시로 간단한 비교를 하지만, 실제로는 서버로 전송하여 검사해야 합니다.
                if (email == 'example@example.com' && password == 'password') {
                  // 로그인 성공 시 처리
                  setState(() {
                    _loginStatus = '로그인 성공!';
                  });
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (context) => DashBoardScreen()),
                  );
                } else {
                  // 로그인 실패 시 처리
                  setState(() {
                    _loginStatus = '로그인 실패!';
                  });
                }
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
            SizedBox(height: 20),
            GestureDetector(
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => singupScreen()),
                );
              },
              child: Text(
                '회원가입',
                style: TextStyle(
                  color: myGrey,
                  decoration: TextDecoration.underline,
                  decorationColor: myGrey,
                  decorationThickness: 2,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
