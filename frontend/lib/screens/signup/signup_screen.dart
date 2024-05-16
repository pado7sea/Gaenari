import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:forsythia/models/users/nickname_check.dart';
import 'package:forsythia/screens/signup/signup2_screen.dart';
import 'package:forsythia/service/member_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/provider/signup_provider.dart';
import 'package:provider/provider.dart';

class SignupScreen extends StatefulWidget {
  const SignupScreen({super.key});

  @override
  State<SignupScreen> createState() => _SignupScreenState();
}

class _SignupScreenState extends State<SignupScreen> {
  final TextEditingController _idcontroller = TextEditingController();
  final TextEditingController _passwordcontroller = TextEditingController();
  final TextEditingController _confirmPasswordcontroller =
      TextEditingController(); // 비밀번호 확인용
  String check = "";

  bool _showErrorMessage = false;
  bool _showCheck = false;

  String _passworderrorText = ''; // 비밀번호 에러텍스트
  String _checkerrorText = ''; // 비밀번호 확인 에러텍스트

  String _iderrorText = ''; // 아이디 확인 에러텍스트

  @override
  void initState() {
    super.initState();
    _passworderrorText = '';
    _checkerrorText = '';
    _iderrorText = '';
    _showErrorMessage = false;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: '회원가입',
        back: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(height: 30),
            maintext,
            SizedBox(height: 30),
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
    child: Center(
      child: RichText(
        textAlign: TextAlign.center,
        text: TextSpan(
          children: const [
            TextSpan(
              text: '아이디',
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
            children: const [
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
                  hintText: '대/소문자와 숫자만 입력가능합니다.',
                  hintStyle: TextStyle(color: Colors.grey),
                  // tap 시 borderline 색상 지정
                  focusedBorder: UnderlineInputBorder(
                      borderSide: BorderSide(color: myBlack)),
                  errorText: _iderrorText.isNotEmpty ? _iderrorText : null,
                ),
                inputFormatters: [
                  FilteringTextInputFormatter.allow(
                      RegExp(r'[a-zA-Z0-9]')), // 영어 대소문자와 숫자만 허용
                ],
                maxLength: 20,
                onChanged: (value) {
                  // 길이를 확인합니다.
                  if (_idcontroller.text.length < 6) {
                    setState(() {
                      _iderrorText = '6자 이상 20자 이하';
                    });
                  } else {
                    setState(() {
                      _iderrorText = '';
                      _showCheck = true;
                    });
                  }
                  setState(() {
                    check = ""; // 값이 변경될 때마다 check 변수를 초기화해줘
                  });
                },
              ))
            ],
          ),
          SizedBox(
            height: 10,
          ),
          Visibility(
            visible: _showCheck,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                ElevatedButton(
                    onPressed: () async {
                      final Check idCheck =
                          await MemberService.fetchIdCheck(_idcontroller.text);
                      setState(() {
                        if (idCheck.data != null && idCheck.data == false) {
                          check = "사용 가능한 아이디";
                        } else {
                          check = "중복된 아이디";
                        }
                      });
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: myLightYellow,
                      elevation: 0,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10),
                      ),
                    ),
                    child: Text12(text: check == "" ? '아이디 중복검사' : check))
              ],
            ),
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
            children: const [
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
            onChanged: (value) {
              // 비밀번호가 형식에 맞는지 체크합니다.
              if (value.length < 6 && value.length <= 12) {
                setState(() {
                  _passworderrorText = '6자 이상 12자 이하';
                });
              } else {
                setState(() {
                  _passworderrorText = '';
                });
              }
            },
            decoration: InputDecoration(
              contentPadding: EdgeInsets.only(left: 5),
              hintText: '비밀번호를 입력해주세요.',
              hintStyle: TextStyle(color: Colors.grey),
              errorStyle: TextStyle(color: myRed, fontSize: 12),
              // tap 시 borderline 색상 지정
              focusedBorder: UnderlineInputBorder(
                borderSide: BorderSide(color: myBlack),
              ),
              errorText: _passworderrorText.isNotEmpty
                  ? _passworderrorText
                  : null, // 에러 메시지를 표시합니다.
            ),
            inputFormatters: [
              FilteringTextInputFormatter.allow(
                  RegExp(r'[a-zA-Z0-9!@#\$%^&*]')), // 영어 대소문자와 숫자, 특수문자만 허용
            ],
            maxLength: 12,
          ),
        ],
      ),
    );
  }

  Widget _checkpassword() {
    _passwordcontroller.addListener(() {
      // 입력된 비밀번호와 비밀번호 확인이 일치하는지 확인합니다.
      if (_confirmPasswordcontroller.text != _passwordcontroller.text) {
        setState(() {
          _checkerrorText = '비밀번호가 일치하지 않습니다.';
        });
      } else {
        setState(() {
          _checkerrorText = '';
        });
      }
    });

    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 10, 30, 10),
      child: Column(
        children: [
          Row(
            children: const [
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
                  _checkerrorText = '비밀번호가 일치하지 않습니다.';
                });
              } else {
                setState(() {
                  _checkerrorText = '';
                });
              }
            },
            decoration: InputDecoration(
              contentPadding: EdgeInsets.only(left: 5),
              hintText: '비밀번호를 위와 동일하게 입력해주세요.',
              hintStyle: TextStyle(color: Colors.grey),
              errorStyle: TextStyle(color: myRed, fontSize: 12),
              // tap 시 borderline 색상 지정
              focusedBorder: UnderlineInputBorder(
                borderSide: BorderSide(color: myBlack),
              ),
              errorText: _checkerrorText.isNotEmpty
                  ? _checkerrorText
                  : null, // 에러 메시지를 표시합니다.
            ),
            maxLength: 12,
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
                  _passworderrorText.isEmpty &&
                  check == "사용 가능한 아이디" &&
                  _passwordcontroller.text.isNotEmpty &&
                  _checkerrorText.isEmpty) {
                setState(() {
                  _showErrorMessage = false; // 에러 메시지 표시
                });
                String accountId = _idcontroller.text;
                print(accountId);
                Provider.of<SignupProvider>(context, listen: false)
                    .setPassword(_passwordcontroller.text);
                Provider.of<SignupProvider>(context, listen: false)
                    .setaccountId(accountId);
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: Signup2Screen()));
              } else {
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
