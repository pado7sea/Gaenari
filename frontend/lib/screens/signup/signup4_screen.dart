import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:carousel_slider/carousel_slider.dart';
import 'package:forsythia/screens/signup/signupend_screen.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/SlidePageRoute.dart';
import 'package:forsythia/widgets/smallAppBar.dart';
import 'package:smooth_page_indicator/smooth_page_indicator.dart';

class signup4Screen extends StatefulWidget {
  const signup4Screen({super.key});

  @override
  State<signup4Screen> createState() => _signup4ScreenState();
}

class _signup4ScreenState extends State<signup4Screen> {
  int activeIndex = 0;
  List<String> images = [
    'assets/emoji/pensil.png',
    'assets/emoji/pensil.png',
    'assets/emoji/pensil.png'
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(
        title: '회원가입',
        back: true,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(alignment: Alignment.topLeft, child: maintext),
            subtext,
            _carousel(),
            _name()
          ],
        ),
      ),
      bottomNavigationBar: _button(),
    );
  }

  var maintext = Padding(
    padding: const EdgeInsets.fromLTRB(20, 50, 100, 0),
    child: RichText(
      // textAlign: TextAlign.center,
      text: TextSpan(
        children: const [
          TextSpan(
            text: '이재신님의 ',
            style: TextStyle(
                color: myBlack,
                fontSize: 22,
                fontWeight: FontWeight.bold,
                fontFamily: 'TheJamsil'),
          ),
          TextSpan(
            text: '강아지',
            style: TextStyle(
                color: myMainGreen,
                fontSize: 22,
                fontWeight: FontWeight.bold,
                fontFamily: 'TheJamsil'),
          ),
          TextSpan(
            text: '를 \n선택해주세요 ',
            style: TextStyle(
                color: myBlack,
                fontSize: 22,
                fontWeight: FontWeight.bold,
                height: 1.5,
                fontFamily: 'TheJamsil'),
          ),
          WidgetSpan(
              child: Image(
            image: AssetImage('assets/emoji/dogface.png'),
            width: 25,
            height: 25,
            fit: BoxFit.cover,
          )),
        ],
      ),
    ),
  );

  var subtext = Padding(
    padding: const EdgeInsets.fromLTRB(20, 10, 100, 50),
    child: Text16(
      text: '대표강아지는 바꾸기 어려우니 신중하게 선택해주세요.',
      textColor: myGrey,
    ),
  );

  // 강아지 캐루젤
  Widget _carousel() {
    return Column(
      children: [
        CarouselSlider.builder(
          itemCount: images.length,
          itemBuilder: (context, index, realIndex) {
            final path = images[index];
            return imageSlider(path, index);
          },
          options: CarouselOptions(
            initialPage: 0,
            viewportFraction: 1,
            enlargeCenterPage: true,
            onPageChanged: (index, reason) => setState(() {
              activeIndex = index;
            }),
          ),
        ),
        indicator()
      ],
    );
  }

  Widget imageSlider(path, index) => Container(
        width: double.infinity,
        height: 240,
        color: Colors.grey,
        child: Image.asset(path, fit: BoxFit.cover),
      );

  Widget indicator() => Container(
      margin: const EdgeInsets.all(10),
      alignment: Alignment.bottomCenter,
      child: AnimatedSmoothIndicator(
        activeIndex: activeIndex,
        count: images.length,
        effect: JumpingDotEffect(
            dotHeight: 6,
            dotWidth: 6,
            activeDotColor: myMainGreen,
            dotColor: myGrey),
      ));

  // 인풋
  Widget _name() {
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
              Text16(text: '  이름')
            ],
          ),
          SizedBox(height: 10),
          TextField(
            decoration: InputDecoration(
                hintText: '강아지의 이름을 입력해주세요.',
                hintStyle: TextStyle(color: Colors.grey),
                // tap 시 borderline 색상 지정
                focusedBorder: UnderlineInputBorder(
                    borderSide: BorderSide(color: myBlack))),
          ),
        ],
      ),
    );
  }

  // 완료 버튼
  Widget _button() {
    return Padding(
      padding: const EdgeInsets.only(bottom: 20),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          ElevatedButton(
            onPressed: () {
              Navigator.of(context)
                  .push(SlidePageRoute(nextPage: signupEndScreen()));
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
              text: '선택완료',
            ),
          ),
        ],
      ),
    );
  }
}
