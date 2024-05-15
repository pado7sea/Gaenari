// ignore_for_file: prefer_const_literals_to_create_immutables, non_constant_identifier_names

import 'package:flutter/material.dart';
import 'package:forsythia/models/records/record_detail.dart';
import 'package:forsythia/service/record_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class DetailRecordScreen extends StatefulWidget {
  final int? recordId;

  const DetailRecordScreen({super.key, required this.recordId});

  @override
  State<DetailRecordScreen> createState() => _DetailRecordScreenState();
}

class _DetailRecordScreenState extends State<DetailRecordScreen> {
  late List<String?> Typelist = [];
  late List<int?> Pacelist = [];
  late List<int?> Heartlist = [];

  Map<int, bool> missionDList = {1: false, 3: false, 5: false, 10: false};
  Map<int, bool> missionTList = {
    10: false,
    30: false,
    50: false,
    100: false,
  };

  late DateTime _recordDateTime;

  RecordDetail recordDetail = RecordDetail();

  bool active = false;

  @override
  void initState() {
    super.initState();
    detailRecordList();
    if (recordDetail.date != null) {
      _recordDateTime = DateTime.parse(recordDetail.date!);
    } else {
      _recordDateTime = DateTime.now();
    }
  }

  void detailRecordList() async {
    DetailRecordList recordList;

    recordList = await RecordSevice.fetchDetailRecordDetail(widget.recordId);
    setState(() {
      active = true;

      recordDetail = recordList.data!;

      Typelist.add(recordDetail.exerciseType);
      Typelist.add(recordDetail.programType);

      for (var value in recordDetail.paces!.arr!) {
        Pacelist.add(value);
      }
      for (var value in recordDetail.heartrates!.arr!) {
        Heartlist.add(value);
      }

      for (var mission in recordDetail.missions!) {
        if (mission.type == 'D') {
          final value = mission.value as int;
          if (missionDList.containsKey(value)) {
            missionDList[value] = true;
          }
        }
      }
      for (var mission in recordDetail.missions!) {
        if (mission.type == 'T') {
          final value = mission.value as int;
          if (missionTList.containsKey(value)) {
            missionTList[value] = true;
          }
        }
      }

      if (recordDetail.date != null) {
        _recordDateTime = DateTime.parse(recordDetail.date!);
      } else {
        _recordDateTime = DateTime.now();
      }
    });
    print('갹ㄴ야ㅐㅓㅁㄴ갸ㅐㅓ ㅑㅐㅔ;ㅁ저ㅐ;');
    print(recordDetail.date);
    print(widget.recordId);
    print(Typelist);
    // print(recordDetail.record?.dailyRecords?[0]?.recordDist);
    print(recordDetail.program?.programTitle ?? 'N/A');
    print(recordDetail.record!.distance);
    print(Pacelist);
    print(Heartlist);
    print(recordDetail.trophies);
    print(missionDList);
    print(missionTList);
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: _day(),
        back: true,
      ),
      body: active
          ? SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.all(20),
                child: Column(
                  children: [
                    _program(),
                    SizedBox(height: 10),
                    _record(),
                    SizedBox(height: 25),
                    _pace(),
                    SizedBox(height: 20),
                    _heart(),
                    recordDetail.trophies!.isNotEmpty
                        ? Padding(
                            padding: const EdgeInsets.symmetric(vertical: 20),
                            child: Column(
                              children: List.generate(
                                  recordDetail.trophies!.length, (index) {
                                return _trophy(index);
                              }),
                            ),
                          )
                        : SizedBox(height: 20),
                    _mission()
                  ],
                ),
              ),
            )
          : Center(
              child: CircularProgressIndicator(),
            ),
    );
  }

  String _day() {
    late String dayOfWeek;

    switch (_recordDateTime.weekday) {
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

    return '${_recordDateTime.year}/${_recordDateTime.month}/${_recordDateTime.day} ($dayOfWeek) ${_recordDateTime.hour}시 ${_recordDateTime.minute}분';
  }

  // 맨 상단 운동 프로그램 정보
  Widget _program() {
    late AssetImage image;
    late String text;

    switch (recordDetail.exerciseType) {
      case 'R':
        image = AssetImage('assets/gif/retriever1_standandlook.gif');
        text = '자유달리기';
        break;
      case 'W':
        image = AssetImage('assets/gif/retriever1_standandlook.gif');
        text = '자유걷기';
        break;
      case 'P':
        image = AssetImage('assets/gif/retriever2_standandlook.gif');
        text = recordDetail.program!.programTitle.toString();
        break;
      default:
        image = AssetImage('assets/gif/retriever1_standandlook.gif');
        text = '알 수 없는 운동';
    }

    return Container(
      height: 55,
      width: double.infinity,
      decoration: myBoxDecoration,
      child: Padding(
        padding: const EdgeInsets.fromLTRB(15, 10, 15, 10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Row(
              children: [
                Image(
                  image: image,
                  height: 40,
                  width: 40,
                  fit: BoxFit.cover,
                  filterQuality: FilterQuality.none,
                ),
                SizedBox(width: 10),
                Text16(
                  text: text,
                  bold: true,
                )
              ],
            ),
            _ifprogram(),
          ],
        ),
      ),
    );
  }

  // 운동프로그램일 때 무슨 목표인지 보여주기
  Widget _ifprogram() {
    String? programType = recordDetail.programType;
    late Color containerColor;
    late String text;

    switch (programType) {
      case 'D':
        containerColor = myLightYellow;
        text = '거리목표';
        break;
      case 'T':
        containerColor = myLightGreen;
        text = '시간목표';
        break;
      case 'I':
        containerColor = myLightRed;
        text = '인터벌목표';
        break;
      default:
        containerColor = Colors.transparent;
        text = '';
    }

    return Container(
      decoration: BoxDecoration(
          color: containerColor, borderRadius: BorderRadius.circular(10)),
      child: Padding(
        padding: const EdgeInsets.fromLTRB(10, 5, 10, 5),
        child: Text12(text: text),
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
                crossAxisAlignment: CrossAxisAlignment.end,
                children: [
                  SizedBox(height: 10),
                  Text20(
                      text: recordDetail.record?.distance?.toStringAsFixed(0) ??
                          'N/A',
                      bold: true),
                  Text12(text: 'km')
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
                  Text20(
                      text:
                          recordDetail.record?.cal?.toStringAsFixed(0) ?? 'N/A',
                      bold: true),
                  Text12(text: 'kcal')
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
                  Text20(
                      text: (recordDetail.record?.time != null
                          ? (recordDetail.record!.time! / 60).toStringAsFixed(0)
                          : 'N/A'),
                      bold: true),
                  Text12(text: 'min')
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
      constraints: BoxConstraints(minHeight: 100),
      decoration: BoxDecoration(
          border: Border.all(color: myWhiteGreen, width: 2),
          borderRadius: BorderRadius.circular(10)),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.all(15.0),
            child: Row(
              children: [
                Text16(text: '페이스', bold: true),
                SizedBox(width: 10),
                Text12(
                  text:
                      '평균 페이스 ${recordDetail.paces!.average!.toInt() ~/ 60}\'${recordDetail.paces!.average!.toInt() % 60}\'\'',
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
                crossAxisAlignment: CrossAxisAlignment.end,
                children: List.generate(Pacelist.length, (index) {
                  // 리스트의 값에 따라 높이를 동적으로 설정
                  double height = (Pacelist[index]! ~/ 60).toDouble();
                  // height가 100을 초과하는 경우 100으로 설정
                  if (height > 30) {
                    height = 30;
                  }
                  return Container(
                    height: height * 2 + 1,
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
      constraints: BoxConstraints(minHeight: 100),
      decoration: BoxDecoration(
          border: Border.all(color: myWhiteGreen, width: 2),
          borderRadius: BorderRadius.circular(10)),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.all(10.0),
            child: Row(
              children: [
                Text16(text: '심박수', bold: true),
                SizedBox(width: 10),
                Text12(
                  text: '평균 심박수 ${recordDetail.heartrates!.average!.toInt()}',
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
                crossAxisAlignment: CrossAxisAlignment.end,
                children: List.generate(Heartlist.length, (index) {
                  // 리스트의 값에 따라 높이를 동적으로 설정
                  double height = Heartlist[index]!.toDouble();
                  return Container(
                    height: height / 2 + 1,
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
                        Text16(
                            text: ' +${recordDetail.attainableHeart}',
                            bold: true),
                        SizedBox(width: 10),
                        Image(
                          image: AssetImage('assets/emoji/money.png'),
                          width: 20,
                          height: 20,
                          fit: BoxFit.cover,
                        ),
                        Text16(
                            text: ' +${recordDetail.attainableCoin}',
                            bold: true)
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
              children: List.generate(missionDList.length, (index) {
                final key = missionDList.keys.elementAt(index);
                final done = missionDList[key] ?? false;

                return MissionCircle(
                  done: done,
                  text: '$key km',
                );
              }),
            ),
          ),
          Padding(
            padding: const EdgeInsets.fromLTRB(0, 10, 0, 20),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: List.generate(missionTList.length, (index) {
                final key = missionTList.keys.elementAt(index);
                final done = missionTList[key] ?? false;

                return MissionCircle(
                  done: done,
                  text: '$key km',
                );
              }),
            ),
          )
        ],
      ),
    );
  }

  Widget _trophy(int index) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 5),
      child: Container(
        height: 55,
        decoration: myTrophyBoxDecoration,
        child: Padding(
          padding: const EdgeInsets.fromLTRB(40, 10, 40, 10),
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
              Text16(
                text: recordDetail.trophies![index].type == 'D'
                    ? '누적 ${recordDetail.trophies![index].value}km 달성'
                    : '누적 ${recordDetail.trophies![index].value}시간 달성',
                bold: true,
              )
            ],
          ),
        ),
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
