import 'dart:async';

import 'package:flutter/material.dart';
import 'package:forsythia/models/watch/watch.dart';
import 'package:forsythia/service/watch_service.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/large_app_bar.dart';

class WatchScreen extends StatefulWidget {
  const WatchScreen({super.key});

  @override
  State<WatchScreen> createState() => _WatchScreenState();
}

class _WatchScreenState extends State<WatchScreen> {
  List<String> tokennumber = ["1", "1", "1", "1"];

  bool again = false;

  @override
  void initState() {
    super.initState();
    _generateRandomNumbers();
    again = true;
  }

  void _generateRandomNumbers() async {
    Watch watch = await WatchService.fetchWatch(context);
    print(watch.data!.split("").toList());
    setState(() {
      tokennumber = watch.data!.split("").toList();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: LargeAppBar(title: '워치 연동하기', sentence: '워치와 개나리를 연동합니다.'),
      body: Padding(
        padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
        child: Column(
          children: [_text(), _number()],
        ),
      ),
      bottomNavigationBar: _button(),
    );
  }

  Widget _text() {
    return Row(children: const [
      Image(
        image: AssetImage('assets/emoji/pensil.png'),
        width: 20,
        height: 20,
        fit: BoxFit.cover,
      ),
      Text16(text: '  인증번호 4자리를 워치에 입력해주세요.', bold: true)
    ]);
  }

  Widget _number() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(50, 100, 50, 0),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Container(
                height: 70,
                width: 50,
                alignment: Alignment.center,
                decoration: BoxDecoration(
                    border: Border.all(color: myMainGreen, width: 2),
                    borderRadius: BorderRadius.circular(10)),
                child: Text20(text: tokennumber[0]),
              ),
              Container(
                height: 70,
                width: 50,
                alignment: Alignment.center,
                decoration: BoxDecoration(
                    border: Border.all(color: myMainGreen, width: 2),
                    borderRadius: BorderRadius.circular(10)),
                child: Text20(text: tokennumber[1]),
              ),
              Container(
                height: 70,
                width: 50,
                alignment: Alignment.center,
                decoration: BoxDecoration(
                    border: Border.all(color: myMainGreen, width: 2),
                    borderRadius: BorderRadius.circular(10)),
                child: Text20(text: tokennumber[2]),
              ),
              Container(
                height: 70,
                width: 50,
                alignment: Alignment.center,
                decoration: BoxDecoration(
                    border: Border.all(color: myMainGreen, width: 2),
                    borderRadius: BorderRadius.circular(10)),
                child: Text20(text: tokennumber[3]),
              )
            ],
          ),
          SizedBox(height: 30),
          CountdownTimer(
            again: again,
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
          ElevatedButton(
            onPressed: () {
              _generateRandomNumbers();
              setState(() {
                again = true;
              });
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
              text: '인증번호 다시받기',
            ),
          ),
        ],
      ),
    );
  }
}

// ignore: must_be_immutable
class CountdownTimer extends StatefulWidget {
  bool again;

  CountdownTimer({super.key, required this.again});

  @override
  // ignore: library_private_types_in_public_api
  _CountdownTimerState createState() => _CountdownTimerState();
}

class _CountdownTimerState extends State<CountdownTimer> {
  int _minutes = 3;
  int _seconds = 0;
  late Timer _timer;

  @override
  void initState() {
    super.initState();
    if (widget.again) {
      _startTimer();
    }
  }

  void _startTimer() {
    _timer = Timer.periodic(Duration(seconds: 1), _updateTimer);
  }

  void _updateTimer(Timer timer) {
    setState(() {
      if (_minutes == 0 && _seconds == 0) {
        _timer.cancel();
        setState(() {
          widget.again = false;
        });
        // 타이머 종료시 추가적인 작업을 수행하고 싶다면 이 곳에 코드를 추가하세요.
      } else if (_seconds == 0) {
        _minutes--;
        _seconds = 59;
      } else {
        _seconds--;
      }
    });
  }

  @override
  void didUpdateWidget(covariant CountdownTimer oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.again) {
      _timer.cancel(); // 기존 타이머 중지
      _minutes = 3; // 초기값으로 재설정
      _seconds = 0; // 초기값으로 재설정
      _startTimer(); // 새로운 타이머 시작
    }
  }

  @override
  void dispose() {
    _timer.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Text12(
          text:
              '$_minutes:${_seconds.toString().padLeft(2, '0')} 뒤에 인증번호가 만료됩니다.',
          textColor: myGrey,
        )
      ],
    );
  }
}
