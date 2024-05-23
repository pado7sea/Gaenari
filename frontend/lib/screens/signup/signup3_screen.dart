import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
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
            _height(),
            _weight(),
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
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              SizedBox(width: 200, child: _heightPicker()),
              Text20(text: 'cm')
            ],
          ),
        ],
      ),
    );
  }

  Widget _heightPicker() {
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

  Widget _weightPicker() {
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
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              SizedBox(width: 200, child: _weightPicker()),
              Text20(text: 'kg')
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
                Provider.of<SignupProvider>(context, listen: false)
                    .setWeight(int.parse(_weightcontroller.text));
                Provider.of<SignupProvider>(context, listen: false)
                    .setHeight(int.parse(_heightcontroller.text));
                Navigator.of(context)
                    .push(SlidePageRoute(nextPage: Signup4Screen()));
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
              text: '다음',
            ),
          ),
        ],
      ),
    );
  }
}
