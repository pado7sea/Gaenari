import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:forsythia/screens/signup/signup3_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/SlidePageRoute.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class signup2Screen extends StatefulWidget {
  const signup2Screen({super.key});

  @override
  State<signup2Screen> createState() => _signup2ScreenState();
}

List<bool> isSelected = [false, false, false];

class _signup2ScreenState extends State<signup2Screen> {
  TextEditingController _nicknamecontroller = TextEditingController();
  TextEditingController _birthcontroller = TextEditingController();

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
          children: [maintext, _nickname(), _birth(), gender()],
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
            children: [
              Image(
                image: AssetImage('assets/emoji/pensil.png'),
                width: 20,
                height: 20,
                fit: BoxFit.cover,
              ),
              Text16(text: '  닉네임')
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
                      hintText: '닉네임을 입력해주세요.',
                      hintStyle: TextStyle(color: Colors.grey),
                      // tap 시 borderline 색상 지정
                      focusedBorder: UnderlineInputBorder(
                          borderSide: BorderSide(color: myBlack))),
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
            children: [
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
              '정보를 모두 입력해주세요!',
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
                  _birthcontroller.text.isNotEmpty) {
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: signup3Screen()));
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
}

class gender extends StatefulWidget {
  const gender({super.key});

  @override
  State<gender> createState() => _genderState();
}

class _genderState extends State<gender> {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 50, 30, 50),
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
              Text16(text: '  성별')
            ],
          ),
          SizedBox(height: 20),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              ToggleButtons(
                isSelected: isSelected,
                onPressed: (int index) {
                  setState(() {
                    for (int buttonIndex = 0;
                        buttonIndex < isSelected.length;
                        buttonIndex++) {
                      if (buttonIndex == index) {
                        isSelected[buttonIndex] = true;
                      } else {
                        isSelected[buttonIndex] = false;
                      }
                    }
                  });
                },
                color: myBlack, // 선택되지 않은 버튼의 텍스트 색상
                selectedColor: Colors.white, // 선택된 버튼의 텍스트 색상
                fillColor: myMainGreen, // 선택된 버튼의 배경 색상
                borderColor: myMainGreen, // 버튼의 테두리 색상
                selectedBorderColor: myMainGreen, // 선택된 버튼의 테두리 색상
                borderRadius: BorderRadius.circular(10),
                borderWidth: 2,
                constraints: BoxConstraints.expand(width: 110), // 버튼의 가로 길이를 지정
                children: <Widget>[
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
