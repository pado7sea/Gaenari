// ignore_for_file: use_build_context_synchronously

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:forsythia/models/programs/program_add.dart';
import 'package:forsythia/service/program_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class AddDistanceProgramPage extends StatefulWidget {
  const AddDistanceProgramPage({super.key});

  @override
  State<AddDistanceProgramPage> createState() => _AddDistanceProgramPageState();
}

class _AddDistanceProgramPageState extends State<AddDistanceProgramPage> {
  final TextEditingController _programName = TextEditingController();
  final TextEditingController _distance = TextEditingController();
  String check = "";
  String _errorText = '';
  List<String> distancelist = [];
  int distanceIndex = 0;

  @override
  void initState() {
    super.initState();
    initializeLists();
  }

  void initializeLists() {
    distancelist = ["0"];
    distancelist.addAll([for (int i = 100; i <= 10000; i += 100) i.toString()]);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(
        title: "프로그램 추가",
        back: true,
      ),
      body: Column(children: [
        SizedBox(height: 40), // 검색창과 텍스트 사이의 간격
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 20), // 검색창 좌우 마진
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text25(
                text: "거리목표",
                bold: true,
              ),
              SizedBox(height: 36),
              Text16(
                text: '프로그램명',
                bold: true,
                textColor: myMainGreen,
              ),
              Row(
                children: [
                  Expanded(
                    // Expanded 위젯을 추가하여 Row의 너비를 확장합니다.
                    child: TextField(
                      controller: _programName,
                      onChanged: (value) {
                        if (value.length < 2 && value.length <= 8) {
                          setState(() {
                            _errorText = '2자 이상 8자 이하';
                          });
                        } else {
                          setState(() {
                            _errorText = '';
                          });
                        }
                        setState(() {
                          check = ""; // 값이 변경될 때마다 check 변수를 초기화해줘
                        });
                      },
                      decoration: InputDecoration(
                        contentPadding: EdgeInsets.only(left: 5),
                        hintText: '운동프로그램의 이름을 정해주세요.',
                        hintStyle: TextStyle(color: Colors.grey),
                        // tap 시 borderline 색상 지정
                        focusedBorder: UnderlineInputBorder(
                            borderSide: BorderSide(color: myBlack)),
                        errorText: _errorText.isNotEmpty ? _errorText : null,
                      ),
                      inputFormatters: [
                        FilteringTextInputFormatter.allow(RegExp(
                            r'[a-zA-Z0-9_ㄱ-힣]')), // 영어 대소문자, 숫자, 언더바, 한글 허용 // 최대 10자까지 입력 허용
                      ],
                      maxLength: 8,
                    ),
                  ),
                ],
              ),
              SizedBox(height: 20),
              Text16(
                text: "목표",
                bold: true,
                textColor: myMainGreen,
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  SizedBox(width: 150, child: _distancePicker()),
                  Text16(text: 'M')
                ],
              ),
              SizedBox(height: 20),
            ],
          ),
        ),
        SizedBox(height: 20),
      ]),
      bottomNavigationBar: GestureDetector(
        onTap: () async {
          if (_programName.text != "" && _distance.text != "0") {
            ProgramAdd program = ProgramAdd(
              programTargetValue: double.parse(_distance.text) * 0.001,
              programTitle: _programName.text,
              programType: "D",
            );
            await ProgramService.fetchProgramAdd(program);
            Navigator.pop(context, "update");
          }
        },
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Container(
            decoration: _programName.text != "" && _distance.text != "0"
                ? myActiveBoxDecoration
                : myNoneBoxDecoration,
            padding: EdgeInsets.all(16),
            height: 55,
            child: Center(
              child: Text16(
                text: "추가",
                bold: true,
                textColor: _programName.text != "" && _distance.text != "0"
                    ? myBlack
                    : myGrey,
              ),
            ),
          ),
        ),
      ),
    );
  }

  Widget _distancePicker() {
    return CupertinoPageScaffold(
      child: Center(
        child: Column(
          children: <Widget>[
            Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                SizedBox(
                  height: 80,
                  child: CupertinoPicker(
                    itemExtent: 50.0,
                    onSelectedItemChanged: (int index) {
                      setState(() {
                        distanceIndex = index;
                        _distance.text = distancelist[distanceIndex];
                      });
                    },
                    children:
                        List<Widget>.generate(distancelist.length, (int index) {
                      return Center(
                          child: Text36(
                        text: distancelist[index],
                        bold: true,
                      ));
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
}
