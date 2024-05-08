// ignore_for_file: use_build_context_synchronously

import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:forsythia/models/users/login_user.dart';
import 'package:forsythia/provider/login_info_provider.dart';
import 'package:forsythia/service/member_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:provider/provider.dart';

class BodyinfoScreen extends StatefulWidget {
  const BodyinfoScreen({super.key});

  @override
  State<BodyinfoScreen> createState() => _BodyinfoScreenState();
}

class _BodyinfoScreenState extends State<BodyinfoScreen> {
  final TextEditingController _heightcontroller = TextEditingController();
  final TextEditingController _weightcontroller = TextEditingController();

  List<String> heightlist = [];
  List<String> weightlist = [];

  @override
  void initState() {
    super.initState();
    initializeLists();
  }

  void initializeLists() {
    // Adding "선택 안함" at the 0th position
    heightlist = ["선택 안함"];
    weightlist = ["선택 안함"];

    heightlist.addAll([for (int i = 120; i <= 230; i++) i.toString()]);
    weightlist.addAll([for (int i = 30; i <= 180; i++) i.toString()]);
  }

  int heightIndex = 0;
  int weightIndex = 0;

  bool _showErrorMessage = false;

  @override
  Widget build(BuildContext context) {
    // 로그인 유저 프로바이더에서 현재 신체정보 가져오기
    var nowWeight = Provider.of<LoginInfoProvider>(context).loginInfo?.weight;
    var nowHeight = Provider.of<LoginInfoProvider>(context).loginInfo?.height;

    var nowheight = nowHeight != null ? nowHeight - 119 : 0;
    var nowweight = nowWeight != null ? nowWeight - 29 : 0;

    return Scaffold(
      appBar: SmallAppBar(
        title: '신체정보',
        back: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(height: 30),
            maintext,
            SizedBox(height: 30),
            _height(nowheight),
            _weight(nowweight),
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
            text: '수정할 신체정보',
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

  Widget _height(int nowheight) {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 10, 30, 20),
      child: Column(
        children: [
          Row(
            children: const [
              Image(
                image: AssetImage('assets/emoji/ruler.png'),
                width: 20,
                height: 20,
                fit: BoxFit.cover,
              ),
              Text16(text: '  키')
            ],
          ),
          SizedBox(height: 10),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              SizedBox(width: 200, child: _heightPicker(nowheight)),
              Text20(text: 'cm')
            ],
          ),
        ],
      ),
    );
  }

  Widget _heightPicker(int nowheight) {
    return CupertinoPageScaffold(
      child: Center(
        child: Column(
          children: <Widget>[
            Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                SizedBox(
                  height: 115,
                  child: CupertinoPicker(
                    scrollController:
                        FixedExtentScrollController(initialItem: nowheight),
                    itemExtent: 32.0,
                    onSelectedItemChanged: (int index) {
                      setState(() {
                        heightIndex = index;
                        _heightcontroller.text = heightlist[heightIndex];
                        print(_heightcontroller.text);
                      });
                    },
                    children:
                        List<Widget>.generate(heightlist.length, (int index) {
                      return Center(child: Text(heightlist[index]));
                    }),
                  ),
                ),
              ],
            ),
            // Container(
            //   padding: EdgeInsets.all(16),
            //   // 사용자가 선택한 과일과 색상을 텍스트로 표시
            //   child: Text(
            //     '선택한 과일: ${heightlist[heightIndex]}, 선택한 색상: ${weightlist[weightIndex]}',
            //     style: TextStyle(fontSize: 16),
            //   ),
            // ),
          ],
        ),
      ),
    );
  }

  Widget _weightPicker(int nowweight) {
    return CupertinoPageScaffold(
      child: Center(
        child: Column(
          children: <Widget>[
            Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                SizedBox(
                  height: 115,
                  child: CupertinoPicker(
                    scrollController:
                        FixedExtentScrollController(initialItem: nowweight),
                    itemExtent: 32.0,
                    onSelectedItemChanged: (int index) {
                      setState(() {
                        weightIndex = index;
                        _weightcontroller.text = weightlist[weightIndex];
                      });
                    },
                    children:
                        List<Widget>.generate(weightlist.length, (int index) {
                      return Center(child: Text(weightlist[index]));
                    }),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _weight(int nowweight) {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 10, 30, 10),
      child: Column(
        children: [
          Row(
            children: const [
              Image(
                image: AssetImage('assets/emoji/eggchicken.png'),
                width: 20,
                height: 20,
                fit: BoxFit.cover,
              ),
              Text16(text: '  몸무게')
            ],
          ),
          SizedBox(height: 10),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              SizedBox(width: 200, child: _weightPicker(nowweight)),
              Text20(text: 'kg')
            ],
          ),
        ],
      ),
    );
  }

  void _fetcheditbodyinfo() async {
    int newHeight = int.parse(heightlist[heightIndex]);
    int newWeight = int.parse(weightlist[weightIndex]);

    try {
      // JSON 데이터 생성
      Map<String, dynamic> requestData = {
        'height': newHeight,
        'weight': newWeight,
      };

      // JSON 데이터를 문자열로 변환
      String jsonData = jsonEncode(requestData);

      // 신체정보 업데이트 요청 보내기
      await MemberService.fetchEditBodyInfo(jsonData);

      // 프로바이더에 업데이트된 닉네임 반영
      LoginInfoProvider loginInfoProvider =
          Provider.of<LoginInfoProvider>(context, listen: false);
      loginInfoProvider.updateLoginInfo(
        LoginInfo(
          memberId: loginInfoProvider.loginInfo?.memberId,
          email: loginInfoProvider.loginInfo?.email,
          nickname: loginInfoProvider.loginInfo?.nickname,
          birthday: loginInfoProvider.loginInfo?.birthday,
          gender: loginInfoProvider.loginInfo?.gender,
          height: newHeight,
          weight: newWeight,
          coin: loginInfoProvider.loginInfo?.coin,
          myPetDto: loginInfoProvider.loginInfo?.myPetDto,
        ),
      );

      // 업데이트 성공 시 EditNickName 화면으로 이동
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
              '키와 몸무게를 선택해주세요.',
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
              if (heightIndex != 0 && weightIndex != 0) {
                _fetcheditbodyinfo();
                setState(() {
                  _showErrorMessage = false;
                });
              } else {
                setState(() {
                  _showErrorMessage = true;
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
