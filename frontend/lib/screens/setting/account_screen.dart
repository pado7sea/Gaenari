import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/SmallAppBar.dart';
import 'package:flutter/services.dart';

class AccountScreen extends StatefulWidget {
  const AccountScreen({super.key});

  @override
  State<AccountScreen> createState() => _AccountScreenState();
}

class _AccountScreenState extends State<AccountScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: '계정',
        back: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(100, 50, 100, 50),
              child: RichText(
                textAlign: TextAlign.center,
                text: TextSpan(
                  children: const [
                    TextSpan(
                      text: '수정할 ',
                      style: TextStyle(
                          color: myBlack,
                          fontSize: 22,
                          fontWeight: FontWeight.bold,
                          fontFamily: 'TheJamsil'),
                    ),
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
                          height: 1.3,
                          fontFamily: 'TheJamsil'),
                    ),
                  ],
                ),
              ),
            ),
            // 여기까지가 메인텍스트
            Padding(
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
                      Text16(text: '  닉네임')
                    ],
                  ),
                  SizedBox(height: 10),
                  TextField(
                    decoration: InputDecoration(
                        hintText: '설정 닉네임',
                        hintStyle: TextStyle(color: Colors.grey),
                        // tap 시 borderline 색상 지정
                        focusedBorder: UnderlineInputBorder(
                            borderSide: BorderSide(color: myBlack))),
                  ),
                  SizedBox(height: 10),
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
            ),
            Padding(
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
                      Text16(text: '  나이')
                    ],
                  ),
                  SizedBox(height: 10),
                  TextField(
                    // 숫자만 입력 가능하게
                    keyboardType: TextInputType.number,
                    inputFormatters: [
                      FilteringTextInputFormatter.digitsOnly,
                    ],
                    decoration: InputDecoration(
                        hintText: '설정 나이',
                        hintStyle: TextStyle(color: Colors.grey),
                        // tap 시 borderline 색상 지정
                        focusedBorder: UnderlineInputBorder(
                            borderSide: BorderSide(color: myBlack))),
                  ),
                ],
              ),
            ),
            SizedBox(
              height: 40,
            ),
            Align(
              alignment: Alignment.bottomCenter,
              child: Padding(
                padding: const EdgeInsets.all(8),
                child: ElevatedButton(
                  onPressed: () {},
                  style: ElevatedButton.styleFrom(
                    backgroundColor: myLightGreen,
                    elevation: 0,
                    minimumSize:
                        Size(MediaQuery.of(context).size.width - 50, 50),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                  ),
                  child: Text16(
                    text: '완료',
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
