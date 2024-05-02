import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:forsythia/screens/signup/signup4_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/slide_page_route.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:forsythia/provider/signup_provider.dart';
import 'package:provider/provider.dart';

class Signup3Screen extends StatefulWidget {
  const Signup3Screen({super.key});

  @override
  State<Signup3Screen> createState() => _Signup3ScreenState();
}

class _Signup3ScreenState extends State<Signup3Screen> {
  TextEditingController _heightcontroller = TextEditingController();
  TextEditingController _weightcontroller = TextEditingController();

  bool _showErrorMessage = false;

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
            _height(),
            _weight(),
          ],
        ),
      ),
      bottomNavigationBar: _button(),
    );
  }

  var maintext = Center(
    child: Padding(
      padding: const EdgeInsets.fromLTRB(100, 50, 100, 50),
      child: RichText(
        textAlign: TextAlign.center,
        text: TextSpan(
          children: const [
            TextSpan(
              text: '키',
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
              text: '몸무게',
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

  Widget _height() {
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
          TextField(
            controller: _heightcontroller,
            // 숫자만 입력 가능하게
            keyboardType: TextInputType.number,
            inputFormatters: [
              FilteringTextInputFormatter.digitsOnly,
            ],
            decoration: InputDecoration(
                hintText: '키를 입력해주세요. (소수점 입력불가)',
                hintStyle: TextStyle(color: Colors.grey),
                // tap 시 borderline 색상 지정
                focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: myBlack))),
          ),
        ],
      ),
    );
  }

  Widget _weight() {
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
          TextField(
            controller: _weightcontroller,
            keyboardType: TextInputType.number,
            inputFormatters: [
              FilteringTextInputFormatter.digitsOnly,
            ],
            decoration: InputDecoration(
                hintText: '몸무게를 입력해주세요. (소수점 입력불가)',
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
              if (_heightcontroller.text.isNotEmpty &&
                  _weightcontroller.text.isNotEmpty) {
                Provider.of<SignupProvider>(context, listen: false)
                    .setWeight(int.parse(_weightcontroller.text));
                Provider.of<SignupProvider>(context, listen: false)
                    .setHeight(int.parse(_heightcontroller.text));
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: Signup4Screen()));
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
