import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:forsythia/screens/signup/signup3_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:forsythia/provider/signup_provider.dart';
import 'package:provider/provider.dart';

class Signup2Screen extends StatefulWidget {
  const Signup2Screen({super.key});

  @override
  State<Signup2Screen> createState() => _Signup2ScreenState();
}

List<bool> isSelected = [false, false, false];

class _Signup2ScreenState extends State<Signup2Screen> {
  String _gender = "OTHER";

  TextEditingController _nicknamecontroller = TextEditingController();
  TextEditingController _birthcontroller = TextEditingController();
  final List<String> _genders = ['MALE', 'FEMALE', 'OTHER']; // 도메인 리스트

  bool _showErrorMessage = false;
  // String _errorText = '';

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
            maintext,
            _nickname(),
            _birth(),
            _genderwidget(),
          ],
        ),
      ),
      bottomNavigationBar: _button(),
    );
  }

  var maintext = Center(
    child: Padding(
      padding: const EdgeInsets.fromLTRB(120, 50, 120, 50),
      child: RichText(
        textAlign: TextAlign.center,
        text: TextSpan(
          children: const [
            TextSpan(
              text: '개인정보',
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

  Widget _nickname() {
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
              Text16(text: '  닉네임'),
            ],
          ),
          Row(
            children: [
              Expanded(
                // Expanded 위젯을 추가하여 Row의 너비를 확장합니다.
                child: TextField(
                  controller: _nicknamecontroller,
                  decoration: InputDecoration(
                    contentPadding: EdgeInsets.only(left: 5),
                    hintText: '대소문자, 숫자, 언더바 포함 3자에서 10자까지 가능.',
                    hintStyle: TextStyle(color: Colors.grey),
                    // tap 시 borderline 색상 지정
                    focusedBorder: UnderlineInputBorder(
                        borderSide: BorderSide(color: myBlack)),
                  ),
                  inputFormatters: [
                    FilteringTextInputFormatter.allow(RegExp(
                        r'[a-zA-Z0-9_]')), // 영어 대소문자, 숫자, 언더바만 허용 // 최대 10자까지 입력 허용
                  ],
                  maxLength: 10,
                ),
              ),
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
                  child: Text12(text: '닉네임 중복검사'))
            ],
          )
        ],
      ),
    );
  }

  Widget _birth() {
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
              Text16(text: '  생년월일')
            ],
          ),
          TextField(
            controller: _birthcontroller,
            // 숫자만 입력 가능하게
            keyboardType: TextInputType.number,
            inputFormatters: [
              FilteringTextInputFormatter.digitsOnly,
            ],
            maxLength: 8,
            decoration: InputDecoration(
                contentPadding: EdgeInsets.only(left: 5),
                hintText: '생년월일 8자를 입력해주세요.',
                hintStyle: TextStyle(color: Colors.grey),
                // tap 시 borderline 색상 지정
                focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: myBlack))),
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
              if (_nicknamecontroller.text.isNotEmpty &&
                  _birthcontroller.text.isNotEmpty &&
                  _nicknamecontroller.text.length >= 3) {
                Provider.of<SignupProvider>(context, listen: false)
                    .setNickName(_nicknamecontroller.text);
                Provider.of<SignupProvider>(context, listen: false)
                    .setBirth(_birthcontroller.text);
                Provider.of<SignupProvider>(context, listen: false)
                    .setGender(_gender);
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: Signup3Screen()));
              } else {
                print('정보를 모두 입력해주세요!');
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

  Widget _genderwidget() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 50, 30, 50),
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
              Text16(text: '  성별')
            ],
          ),
          SizedBox(height: 20),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              ToggleButtons(
                isSelected: [
                  _gender == _genders[0],
                  _gender == _genders[1],
                  _gender == _genders[2],
                ],
                onPressed: (int index) {
                  setState(() {
                    switch (index) {
                      case 0:
                        _gender = _genders[0];
                        break;
                      case 1:
                        _gender = _genders[1];
                        break;
                      case 2:
                        _gender = _genders[2];
                        break;
                    }
                  });
                },
                color: myBlack,
                selectedColor: Colors.white,
                fillColor: myMainGreen,
                borderColor: myMainGreen,
                selectedBorderColor: myMainGreen,
                borderRadius: BorderRadius.circular(10),
                borderWidth: 2,
                constraints: BoxConstraints.expand(width: 110),
                children: const <Widget>[
                  Text('남자'),
                  Text('여자'),
                  Text('선택안함'),
                ],
              ),
            ],
          ),
        ],
      ),
    );
  }
}
