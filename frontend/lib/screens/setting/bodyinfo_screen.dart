import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class BodyinfoScreen extends StatefulWidget {
  const BodyinfoScreen({super.key});

  @override
  State<BodyinfoScreen> createState() => _BodyinfoScreenState();
}

class _BodyinfoScreenState extends State<BodyinfoScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(
        title: '신체정보',
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
                      text: '신체정보',
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
                        image: AssetImage('assets/emoji/ruler.png'),
                        width: 20,
                        height: 20,
                        fit: BoxFit.cover,
                      ),
                      Text16(text: '  키')
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
                        hintText: '설정 키',
                        hintStyle: TextStyle(color: Colors.grey),
                        // tap 시 borderline 색상 지정
                        focusedBorder: UnderlineInputBorder(
                            borderSide: BorderSide(color: myBlack))),
                  ),
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
                        image: AssetImage('assets/emoji/eggchicken.png'),
                        width: 20,
                        height: 20,
                        fit: BoxFit.cover,
                      ),
                      Text16(text: '  몸무게')
                    ],
                  ),
                  SizedBox(height: 10),
                  TextField(
                    keyboardType: TextInputType.number,
                    inputFormatters: [
                      FilteringTextInputFormatter.digitsOnly,
                    ],
                    decoration: InputDecoration(
                        hintText: '설정 몸무게',
                        hintStyle: TextStyle(color: Colors.grey),
                        // tap 시 borderline 색상 지정
                        focusedBorder: UnderlineInputBorder(
                            borderSide: BorderSide(color: myBlack))),
                  ),
                ],
              ),
            ),
            SizedBox(
              height: 290,
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
