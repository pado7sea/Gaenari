// ignore_for_file: use_build_context_synchronously

import 'package:flutter/material.dart';
import 'package:forsythia/models/users/password_check.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:flutter/services.dart';
import 'package:forsythia/service/member_service.dart';

class EditPassword extends StatefulWidget {
  const EditPassword({super.key});

  @override
  State<EditPassword> createState() => _EditPasswordState();
}

class _EditPasswordState extends State<EditPassword> {
  final TextEditingController _nowpasswordcontroller =
      TextEditingController(); // 현재비밀번호 입력
  final TextEditingController _passwordcontroller =
      TextEditingController(); // 변경비밀번호 입력
  final TextEditingController _confirmPasswordcontroller =
      TextEditingController(); // 비밀번호 확인용

  String check = "";

  bool _showTextField = false; // 비밀번호 확인 되면 입력 보여주기
  bool _showErrorMessage = false;

  String _passworderrorText = ''; // 비밀번호 에러텍스트
  String _checkerrorText = ''; // 비밀번호 확인 에러텍스트
  @override
  void initState() {
    super.initState();
    _passworderrorText = '';
    _checkerrorText = '';
    _showErrorMessage = false;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: '비밀번호',
        back: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(height: 30),
            maintext,
            SizedBox(height: 30),
            if (!_showTextField) _nowpassword(),
            if (_showTextField)
              Column(
                children: [
                  _password(),
                  _checkpassword(),
                ],
              ),
          ],
        ),
      ),
      bottomNavigationBar: _button(),
    );
  }

  var maintext = Center(
    child: RichText(
      textAlign: TextAlign.center,
      text: TextSpan(
        children: const [
          TextSpan(
            text: '변경할 비밀번호',
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
  );

  Widget _nowpassword() {
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
              Text16(text: '  현재 비밀번호')
            ],
          ),
          TextField(
            controller: _nowpasswordcontroller,
            obscureText: true, // 비밀번호 입력을 숨깁니다.
            onChanged: (value) {
              setState(() {
                check = ""; // 값이 변경될 때마다 check 변수를 초기화해줘
              });
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
          SizedBox(
            height: 10,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            children: [
              ElevatedButton(
                  onPressed: () async {
                    final PasswordCheck nowpasswordCheck =
                        await MemberService.fetchNowPassWord(
                            context, _nowpasswordcontroller.text);
                    setState(() {
                      if (nowpasswordCheck.data == 1) {
                        check = "비밀번호 확인완료";
                        _showTextField = true;
                      } else {
                        check = "비밀번호 확인실패";
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
                  child: Text12(text: check == "" ? '비밀번호 확인' : check))
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
            children: const [
              Image(
                image: AssetImage('assets/emoji/pensil.png'),
                width: 20,
                height: 20,
                fit: BoxFit.cover,
              ),
              Text16(text: '  변경 비밀번호')
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
              hintText: '변경할 비밀번호를 입력해주세요.',
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
              Text16(text: '  변경 비밀번호 확인')
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

  void _fetcheditpassword() async {
    try {
      await MemberService.fetchEditPassWord(context, _passwordcontroller.text);

      Navigator.of(context).pop();
    } catch (error) {
      print(error);
    }
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
              if (_passworderrorText.isEmpty &&
                  _passwordcontroller.text.isNotEmpty &&
                  _checkerrorText.isEmpty) {
                _fetcheditpassword();
                setState(() {
                  _showErrorMessage = false; // 에러 메시지 표시
                });
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
              text: '변경 완료',
            ),
          ),
        ],
      ),
    );
  }
}
