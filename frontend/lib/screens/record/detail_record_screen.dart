// ignore_for_file: prefer_const_literals_to_create_immutables

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class DetailRecordScreen extends StatefulWidget {
  final DateTime selectedDate;

  const DetailRecordScreen({super.key, required this.selectedDate});

  @override
  State<DetailRecordScreen> createState() => _DetailRecordScreenState();
}

class _DetailRecordScreenState extends State<DetailRecordScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: '기록 상세',
        back: true,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(20),
          child: Column(
            children: [
              _day(),
              SizedBox(height: 20),
              _program(),
              SizedBox(height: 10),
              _record(),
              SizedBox(height: 25),
              _pace(),
              SizedBox(height: 20),
              _heart(),
              SizedBox(height: 25),
              Trophy(text: '256KM'),
              SizedBox(height: 10),
              Trophy(text: '256시간'),
              SizedBox(height: 25),
              _mission()
            ],
          ),
        ),
      ),
    );
  }

  Widget _day() {
    // 요일 문자열을 담을 변수
    late String dayOfWeek;

    // selectedDate의 요일을 확인하여 문자열에 저장
    switch (widget.selectedDate.weekday) {
      case 1:
        dayOfWeek = '월';
        break;
      case 2:
        dayOfWeek = '화';
        break;
      case 3:
        dayOfWeek = '수';
        break;
      case 4:
        dayOfWeek = '목';
        break;
      case 5:
        dayOfWeek = '금';
        break;
      case 6:
        dayOfWeek = '토';
        break;
      case 7:
        dayOfWeek = '일';
        break;
      default:
        dayOfWeek = '';
    }

    return Text20(
      text:
          '${widget.selectedDate.year}/${widget.selectedDate.month}/${widget.selectedDate.day}  ($dayOfWeek)  ${widget.selectedDate.hour}:${widget.selectedDate.minute}',
      bold: true,
    );
  }

  Widget _program() {
    return Container(
      height: 55,
      width: double.infinity,
      decoration: myBoxDecoration,
      child: Padding(
        padding: const EdgeInsets.fromLTRB(15, 10, 15, 10),
        child: Row(
          children: [
            Image(
              image: AssetImage('assets/gif/retriever1_standandlook.gif'),
              height: 40,
              width: 40,
              fit: BoxFit.cover,
              filterQuality: FilterQuality.none,
            ),
            SizedBox(width: 10),
            Text16(
              text: '자유걷기',
              bold: true,
            ),
          ],
        ),
      ),
    );
  }

  Widget _record() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      crossAxisAlignment: CrossAxisAlignment.end,
      children: [
        Row(
          children: [
            // 총 거리
            Image(
              image: AssetImage('assets/emoji/running.png'),
              width: 25,
              height: 35,
              fit: BoxFit.cover,
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
              child: Column(
                children: [
                  SizedBox(height: 10),
                  Text20(text: '10.9', bold: true),
                  Text12(text: '총 거리')
                ],
              ),
            ),
          ],
        ),
        Container(
          width: 2,
          height: 60,
          color: myGrey,
        ),
        Row(
          children: [
            // 총 칼로리
            Image(
              image: AssetImage('assets/emoji/fire.png'),
              width: 25,
              height: 30,
              fit: BoxFit.cover,
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
              child: Column(
                children: [
                  SizedBox(height: 10),
                  Text20(text: '10.9', bold: true),
                  Text12(text: '총 칼로리')
                ],
              ),
            ),
          ],
        ),
        Container(
          width: 2,
          height: 60,
          color: myGrey,
        ),
        Row(
          children: [
            // 총 시간
            Image(
              image: AssetImage('assets/emoji/clock.png'),
              width: 25,
              height: 25,
              fit: BoxFit.cover,
            ),
            Padding(
              padding: const EdgeInsets.fromLTRB(10, 20, 10, 0),
              child: Column(
                children: [
                  SizedBox(height: 10),
                  Text20(text: '10.9', bold: true),
                  Text12(text: '총 시간')
                ],
              ),
            ),
          ],
        )
      ],
    );
  }

  Widget _pace() {
    return Container(
      decoration: BoxDecoration(
          border: Border.all(color: myWhiteGreen, width: 2),
          borderRadius: BorderRadius.circular(10)),
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(15.0),
            child: Row(
              children: [
                Text16(text: '페이스', bold: true),
                SizedBox(width: 10),
                Text12(
                  text: '평균 페이스',
                  textColor: myGrey,
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.fromLTRB(15, 5, 15, 0),
            child: SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Row(
                children: List.generate(50, (index) {
                  return Container(
                    height: 40,
                    width: 3,
                    margin: EdgeInsets.only(right: 3), // 각 콘테이너 사이의 간격 조정
                    color: myBlue, // 콘테이너 색상 설정
                  );
                }),
              ),
            ),
          )
        ],
      ),
    );
  }

  Widget _heart() {
    return Container(
      decoration: BoxDecoration(
          border: Border.all(color: myWhiteGreen, width: 2),
          borderRadius: BorderRadius.circular(10)),
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(10.0),
            child: Row(
              children: [
                Text16(text: '심박수', bold: true),
                SizedBox(width: 10),
                Text12(
                  text: '평균 심박수',
                  textColor: myGrey,
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.fromLTRB(15, 5, 15, 0),
            child: SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Row(
                children: List.generate(100, (index) {
                  return Container(
                    height: 40,
                    width: 3,
                    margin: EdgeInsets.only(right: 3), // 각 콘테이너 사이의 간격 조정
                    color: myRed, // 콘테이너 색상 설정
                  );
                }),
              ),
            ),
          )
        ],
      ),
    );
  }

  Widget _mission() {
    return Container(
      decoration: BoxDecoration(
          color: myWhiteGreen, borderRadius: BorderRadius.circular(10)),
      child: Column(
        children: [
          Padding(
            padding: const EdgeInsets.fromLTRB(30, 20, 20, 20),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Row(
                  children: [
                    Text16(text: '  미션', bold: true),
                    IconButton(
                      icon: Icon(Icons.info_outline), // 정보를 나타내는 아이콘으로 변경 가능
                      onPressed: () {
                        showDialog(
                          context: context, // 현재 컨텍스트를 전달합니다.
                          builder: (BuildContext context) {
                            return AlertDialog(
                              backgroundColor: myBackground,
                              title: Text('미션'), // 다이얼로그 제목
                              content: Column(
                                mainAxisSize: MainAxisSize.min,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: <Widget>[
                                  Text('뭐하면 애정도 상승 저쩌구'), // 미션 내용 추가
                                  // 다른 미션 정보 내용 추가 가능
                                ],
                              ),
                              actions: <Widget>[
                                TextButton(
                                    onPressed: () {
                                      Navigator.of(context).pop(); // 다이얼로그 닫기
                                    },
                                    child: Text16(
                                      text: '닫기',
                                      textColor: myBlack,
                                    ) // 닫기 버튼 텍스트
                                    ),
                              ],
                            );
                          },
                        );
                      },
                    ),
                  ],
                ),
                Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(100),
                    color: Colors.white,
                    boxShadow: [
                      BoxShadow(
                        color: Color(0xffBFC2C8).withOpacity(0.25),
                        blurRadius: 15,
                        offset: Offset(0, 10),
                      ),
                    ],
                  ),
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(15, 8, 15, 8),
                    child: Row(
                      children: [
                        Image(
                          image: AssetImage('assets/emoji/heart.png'),
                          width: 20,
                          height: 20,
                          fit: BoxFit.cover,
                        ),
                        Text16(text: ' + 3', bold: true),
                        SizedBox(width: 10),
                        Image(
                          image: AssetImage('assets/emoji/money.png'),
                          width: 20,
                          height: 20,
                          fit: BoxFit.cover,
                        ),
                        Text16(text: ' + 500', bold: true)
                      ],
                    ),
                  ),
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.fromLTRB(0, 0, 0, 10),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                MissionCircle(
                  done: true,
                  text: '1KM',
                ),
                MissionCircle(
                  done: false,
                  text: '3KM',
                ),
                MissionCircle(
                  done: false,
                  text: '5KM',
                ),
                MissionCircle(
                  done: false,
                  text: '10KM',
                )
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.fromLTRB(0, 10, 0, 20),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                MissionCircle(
                  done: true,
                  text: '10분',
                ),
                MissionCircle(
                  done: false,
                  text: '30분',
                ),
                MissionCircle(
                  done: false,
                  text: '60분',
                ),
                MissionCircle(
                  done: false,
                  text: '100분',
                )
              ],
            ),
          )
        ],
      ),
    );
  }
}

class MissionCircle extends StatelessWidget {
  final String text; // 글씨
  final bool done; // 완료여부
  const MissionCircle({
    super.key,
    required this.text,
    this.done = false,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center,
      height: 60,
      width: 60,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(100),
        border: done ? Border.all(color: myLightGreen, width: 4) : null,
      ),
      child: Text(text,
          style: TextStyle(
            fontSize: 16,
            color: done ? myLightGreen : myGrey,
            fontWeight: done ? FontWeight.bold : FontWeight.normal,
          )),
    );
  }
}

class Trophy extends StatelessWidget {
  final String text; // 글씨
  const Trophy({
    super.key,
    required this.text,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: myTrophyBoxDecoration,
      child: Padding(
        padding: const EdgeInsets.fromLTRB(30, 10, 30, 10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: [
            Image(
              image: AssetImage('assets/emoji/trophy.png'),
              width: 25,
              height: 25,
              fit: BoxFit.cover,
            ),
            SizedBox(width: 10),
            Text16(text: '누적 $text 달성', bold: true)
          ],
        ),
      ),
    );
  }
}
