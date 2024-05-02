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
    'assets/gif/shepherd_standandlook.gif',
    'assets/gif/grayhound_standandlook.gif',
    'assets/gif/husky_standandlook.gif',
    'assets/gif/pomeranian1_standandlook.gif',
    'assets/gif/pomeranian2_standandlook.gif',
    'assets/gif/shiba_standandlook.gif',
    'assets/gif/pug_standandlook.gif',
    'assets/gif/retriever1_standandlook.gif',
    'assets/gif/retriever2_standandlook.gif',
    'assets/gif/wolf_standandlook.gif',
  ];

  List<String> dogbreeds = [
    '셰퍼드',
    '그레이하운드',
    '허스키',
    '갈색 포메라니안',
    '흰색 포메라니안',
    '시바',
    '퍼그',
    '갈색 리트리버',
    '흰색 리트리버',
    '늑대',
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
            Container(alignment: Alignment.topLeft, child: subtext),
            _carousel(),
            SizedBox(
              height: 20,
            ),
            _name()
          ],
        ),
      ),
      bottomNavigationBar: _button(),
    );
  }

  var maintext = Padding(
    padding: const EdgeInsets.fromLTRB(20, 50, 0, 1),
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
            text: '를 선택해주세요 ',
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
      text: '대표강아지는 바꾸기 어려우니 \n신중하게 선택해주세요.',
      textColor: myGrey,
      bold: true,
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
        indicator(),
        SizedBox(
          height: 10,
        ),
        _dogbreed(activeIndex),
      ],
    );
  }

  Widget imageSlider(path, index) => Container(
        width: double.infinity,
        height: 300,
        alignment: Alignment.center,
        decoration: BoxDecoration(
          color: myLightYellow, // 배경색을 투명하게 설정
          shape: BoxShape.circle, // 원형 모양으로 설정
        ),
        child: Padding(
          padding: const EdgeInsets.fromLTRB(0, 0, 10, 40),
          child: Image(
            image: AssetImage(path),
            height: 140,
            width: 140,
            fit: BoxFit.cover,
            filterQuality: FilterQuality.none,
          ),
        ),
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

  Widget _dogbreed(int index) {
    // 현재 이미지 슬라이더의 인덱스에 해당하는 breed를 가져옴
    String breed = dogbreeds[index];
    return Container(
      child: Text20(
        text: breed,
        bold: true,
      ),
    );
  }

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
