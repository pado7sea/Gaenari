import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:forsythia/models/users/nickname_check.dart';
import 'package:forsythia/screens/signup/signup3_screen.dart';
import 'package:forsythia/service/member_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:forsythia/provider/signup_provider.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';

class Signup2Screen extends StatefulWidget {
  const Signup2Screen({super.key});

  @override
  State<Signup2Screen> createState() => _Signup2ScreenState();
}

List<bool> isSelected = [false, false, false];

class _Signup2ScreenState extends State<Signup2Screen> {
  String _gender = "OTHER";

  final TextEditingController _nicknamecontroller = TextEditingController();
  final TextEditingController _birthcontroller = TextEditingController();
  final List<String> _genders = ['MALE', 'FEMALE', 'OTHER']; // 도메인 리스트
  String check = "";

  bool _showErrorMessage = false;
  bool _showCheck = false;

  String _errorText = '';
  @override
  void initState() {
    super.initState();
    _errorText = '';
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
                  onChanged: (value) {
                    if (value.length < 2 && value.length <= 10) {
                      setState(() {
                        _errorText = '2자 이상 10자 이하';
                      });
                    } else {
                      setState(() {
                        _errorText = '';
                        _showCheck = true;
                      });
                    }
                    setState(() {
                      check = ""; // 값이 변경될 때마다 check 변수를 초기화해줘
                    });
                  },
                  decoration: InputDecoration(
                    contentPadding: EdgeInsets.only(left: 5),
                    hintText: '닉네임을 입력해주세요.',
                    hintStyle: TextStyle(color: Colors.grey),
                    // tap 시 borderline 색상 지정
                    focusedBorder: UnderlineInputBorder(
                        borderSide: BorderSide(color: myBlack)),
                    errorText: _errorText.isNotEmpty ? _errorText : null,
                  ),
                  inputFormatters: [
                    FilteringTextInputFormatter.allow(RegExp(
                        r'[a-zA-Z0-9ㄱ-힣]')), // 영어 대소문자, 숫자, 한글 허용 // 최대 10자까지 입력 허용
                  ],
                  maxLength: 10,
                ),
              ),
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
                          await MemberService.fetchNickNameCheck(
                              context, _nicknamecontroller.text);
                      setState(() {
                        if (idCheck.data != null && idCheck.data == false) {
                          check = "사용 가능한 닉네임";
                        } else {
                          check = "중복된 닉네임";
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
                    child: Text12(text: check == "" ? '닉네임 중복검사' : check))
              ],
            ),
          ),
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
          SizedBox(height: 20),
          CustomDatePicker(
            onDateChange: (String newDate) {
              print(_birthcontroller.text);
              _birthcontroller.text = newDate;
            },
          ),
        ],
      ),
    );
  }

  Widget _genderwidget() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 40, 30, 30),
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
                constraints: BoxConstraints.expand(
                    width: (MediaQuery.of(context).size.width - 40) / 4),
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
                  check != "" && //닉네임 체크
                  _nicknamecontroller.text.length >= 2) {
                Provider.of<SignupProvider>(context, listen: false)
                    .setNickName(_nicknamecontroller.text);
                Provider.of<SignupProvider>(context, listen: false)
                    .setBirth(_birthcontroller.text);
                Provider.of<SignupProvider>(context, listen: false)
                    .setGender(_gender);
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: Signup3Screen()));
                setState(() {
                  _showErrorMessage = false; // 에러 메시지 표시
                });
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

class CustomDatePicker extends StatefulWidget {
  final Function(String) onDateChange;
  const CustomDatePicker({super.key, required this.onDateChange});

  @override
  State<CustomDatePicker> createState() => _CustomDatePickerState();
}

class _CustomDatePickerState extends State<CustomDatePicker> {
  // 선택된 날짜 및 시간을 저장할 변수를 선언합니다. 초기값으로 현재 날짜와 시간을 설정할 수 있습니다.
  DateTime date = DateTime.now();

  // 새로운 날짜와 시간을 date 변수에 저장하고, 상태를 업데이트하기 위해 setState를 호출합니다.
  void _onDateTimeChanged(DateTime newDate) {
    setState(() {
      date = newDate;
      widget.onDateChange(DateFormat('yyyy-MM-dd').format(date));
    });
  }

  @override
  Widget build(BuildContext context) {
    // CupertinoPageScaffold를 사용하여 iOS 스타일의 페이지 레이아웃을 구성합니다.
    return CupertinoPageScaffold(
      child: Center(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            SizedBox(
              height: 67,
              // 날짜 및 시간을 선택할 수 있는 CupertinoDatePicker 위젯.
              child: CupertinoDatePicker(
                // dateOrder: DatePickerDateOrder.ymd,
                // 초기에 선택된 날짜와 시간.
                initialDateTime: date,
                // 날짜나 시간이 변경될 때 호출되는 콜백 함수.
                onDateTimeChanged: _onDateTimeChanged,
                // 날짜와 시간을 모두 선택할 수 있도록 설정.
                mode: CupertinoDatePickerMode.date,
                // 24시간 형식을 사용하도록 설정.
                use24hFormat: true,
                // 선택 가능한 최소 연도 설정.
                minimumYear: 1910,
                // 선택 가능한 최대 연도를 현재 연도로 설정.
                maximumYear: DateTime.now().year,
                maximumDate: DateTime.now(),
              ),
            ),
            // TextField(
            //   enabled: false, // 입력 비활성화
            //   decoration: InputDecoration(
            //     contentPadding: EdgeInsets.only(left: 5),
            //     hintText: DateFormat('yyyy-MM-dd').format(date),
            //     hintStyle: TextStyle(color: myBlack),
            //     disabledBorder: UnderlineInputBorder(
            //         borderSide: BorderSide(color: myBlack, width: 0.8)),
            //   ),
            // ),
          ],
        ),
      ),
    );
  }
}
