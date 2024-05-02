import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:forsythia/screens/signup/signup2_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/SlidePageRoute.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class singupScreen extends StatefulWidget {
  const singupScreen({super.key});

  @override
  State<singupScreen> createState() => _singupScreenState();
}

class _singupScreenState extends State<singupScreen> {
  TextEditingController _idcontroller = TextEditingController();
  TextEditingController _passwordcontroller = TextEditingController();
  TextEditingController _confirmPasswordcontroller =
      TextEditingController(); // 비밀번호 확인용
  List<String> _domains = ['naver.com', 'gmail.com', 'hotmail.com']; // 도메인 리스트
  String _selectedDomain = ''; // 선택된 도메인
  bool _showErrorMessage = false;
  String _errorText = '';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(
        title: '회원가입',
        back: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            maintext,
            _id(),
            _password(),
            SizedBox(height: 10),
            _checkpassword(),
            SizedBox(
              height: 20,
            )
          ],
        ),
      ),
      bottomNavigationBar: _button(),
    );
  }

  // 상단 메인텍스트
  var maintext = Center(
    child: Padding(
      padding: const EdgeInsets.fromLTRB(100, 50, 100, 50),
      child: RichText(
        textAlign: TextAlign.center,
        text: TextSpan(
          children: const [
            TextSpan(
              text: '이메일',
              style: TextStyle(
                  color: myMainGreen,
                  fontSize: 22,
                  fontWeight: FontWeight.bold,
                  fontFamily: 'TheJamsil'),
            ),
            TextSpan(
              text: '와 ',
              style: TextStyle(
                  color: myBlack,
                  fontSize: 22,
                  fontWeight: FontWeight.bold,
                  fontFamily: 'TheJamsil'),
            ),
            TextSpan(
              text: '비밀번호',
              style: TextStyle(
                  color: myMainGreen,
                  fontSize: 22,
                  fontWeight: FontWeight.bold,
                  fontFamily: 'TheJamsil'),
            ),
            TextSpan(
              text: '를 \n입력해주세요.',
              style: TextStyle(
                  color: myBlack,
                  fontSize: 22,
                  fontWeight: FontWeight.bold,
                  height: 1.5,
                  fontFamily: 'TheJamsil'),
            ),
          ],
        ),
      ),
    ),
  );

  Widget _id() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 10, 30, 20),
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
              Text16(text: '  아이디')
            ],
          ),
          Row(
            children: [
              Expanded(
                // Expanded 위젯을 추가하여 Row의 너비를 확장합니다.
                child: TextField(
                  controller: _idcontroller,
                  decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 5),
                      hintText: 'example',
                      hintStyle: TextStyle(color: Colors.grey),
                      // tap 시 borderline 색상 지정
                      focusedBorder: UnderlineInputBorder(
                          borderSide: BorderSide(color: myBlack))),
                  inputFormatters: [
                    FilteringTextInputFormatter.allow(
                        RegExp(r'[a-zA-Z0-9]')), // 영어 대소문자와 숫자만 허용
                  ],
                ),
              ),
              Text(' @ '),
              Expanded(
                child: DropdownButtonFormField(
                  value: _selectedDomain.isNotEmpty ? _selectedDomain : null,
                  items: _domains.map((domain) {
                    return DropdownMenuItem(
                      value: domain,
                      child: Text(domain),
                    );
                  }).toList(),
                  onChanged: (value) {
                    setState(() {
                      _selectedDomain = value!;
                    });
                  },
                  decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 5),
                      hintText: 'example.com',
                      hintStyle: TextStyle(color: Colors.grey),
                      focusedBorder: UnderlineInputBorder(
                          borderSide: BorderSide(color: myBlack))),
                ),
              )
            ],
          ),
          SizedBox(
            height: 10,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              ElevatedButton(
                  onPressed: () {
                    //
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: myLightYellow,
                    elevation: 0,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                  child: Text12(text: '아이디 중복검사'))
            ],
          )
        ],
      ),
    );
  }

  Widget _password() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 10, 30, 10),
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
              Text16(text: '  비밀번호')
            ],
          ),
          TextField(
            controller: _passwordcontroller,
            obscureText: true, // 비밀번호 입력을 숨깁니다.
            decoration: InputDecoration(
                contentPadding: EdgeInsets.only(left: 5),
                hintText: '대소문자, 숫자포함 6자에서 16자까지 가능.',
                hintStyle: TextStyle(color: Colors.grey),
                // tap 시 borderline 색상 지정
                focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: myBlack))),
            inputFormatters: [
              FilteringTextInputFormatter.allow(
                  RegExp(r'[a-zA-Z0-9]')), // 영어 대소문자와 숫자만 허용
            ],
            maxLength: 16,
          ),
        ],
      ),
    );
  }

  Widget _checkpassword() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 10, 30, 10),
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
              Text16(text: '  비밀번호 확인')
            ],
          ),
          TextField(
            controller: _confirmPasswordcontroller,
            obscureText: true, // 비밀번호 입력을 숨깁니다.
            onChanged: (value) {
              // 입력된 비밀번호와 비밀번호 확인이 일치하는지 확인합니다.
              if (_passwordcontroller.text != value) {
                setState(() {
                  _errorText = '비밀번호가 일치하지 않습니다.';
                });
              } else {
                setState(() {
                  _errorText = '';
                });
              }
            },
            decoration: InputDecoration(
              contentPadding: EdgeInsets.only(left: 5),
              hintText: '비밀번호를 위와 동일하게 입력해주세요.',
              hintStyle: TextStyle(color: Colors.grey),
              errorStyle: TextStyle(color: myRed, fontSize: 16),
              // tap 시 borderline 색상 지정
              focusedBorder: UnderlineInputBorder(
                borderSide: BorderSide(color: myBlack),
              ),
              errorText:
                  _errorText.isNotEmpty ? _errorText : null, // 에러 메시지를 표시합니다.
            ),
            maxLength: 16,
          ),
        ],
      ),
    );
  }

  Widget _button() {
    return Padding(
      padding: const EdgeInsets.only(bottom: 20),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          if (_showErrorMessage) // 상태에 따라 텍스트를 표시하거나 숨김
            Text(
              '모든 입력이 올바른지 확인해주세요.',
              textAlign: TextAlign.center,
              style: TextStyle(
                fontFamily: 'TheJamsil',
                color: myRed,
                fontSize: 16,
              ),
            ),
          SizedBox(
            height: 10,
          ),
          ElevatedButton(
            onPressed: () {
              if (_idcontroller.text.isNotEmpty &&
                  _selectedDomain.isNotEmpty &&
                  _passwordcontroller.text.isNotEmpty &&
                  _confirmPasswordcontroller.text.isNotEmpty &&
                  _passwordcontroller.text == _confirmPasswordcontroller.text &&
                  _passwordcontroller.text.length >= 6) {
                String email = _idcontroller.text + '@' + _selectedDomain;
                print(email); // 여기서는 저장하지 않고 콘솔에 출력
                // 저장하려면 따로 데이터베이스나 파일에 저장하는 등의 작업이 필요해
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: signup2Screen()));
              } else {
                print('모든 입력이 올바른지 확인해주세요.');
                setState(() {
                  _showErrorMessage = true; // 에러 메시지 표시
                });
              }
            },
            style: ElevatedButton.styleFrom(
              backgroundColor: myLightGreen,
              elevation: 0,
              minimumSize: Size(MediaQuery.of(context).size.width - 50, 50),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10),
              ),
            ),
            child: Text16(
              text: '다음',
            ),
          ),
        ],
      ),
    );
  }
}
