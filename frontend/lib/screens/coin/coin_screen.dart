import 'package:flutter/material.dart';
import 'package:forsythia/theme/color.dart';
import 'package:forsythia/theme/text.dart';
import 'package:forsythia/widgets/box_dacoration.dart';
import 'package:forsythia/widgets/small_app_bar.dart';
import 'package:intl/intl.dart';

class CoinScreen extends StatefulWidget {
  const CoinScreen({super.key});

  @override
  State<CoinScreen> createState() => _CoinScreenState();
}

class _CoinScreenState extends State<CoinScreen> {
  bool pickerIsExpanded = false;
  int _pickerYear = DateTime.now().year;
  int _pickerMonth = DateTime.now().month;
  DateTime _selectedMonth = DateTime.now();

  bool isplus = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(title: '코인내역', back: true),
      body: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          _monthPicker(),
          SizedBox(
            height: 20.0,
          ),
          Text(DateFormat.yMMMM().format(_selectedMonth)),
          _coin()
        ],
      ),
    );
  }

  Widget _monthPicker() {
    return Padding(
      padding: const EdgeInsets.fromLTRB(30, 10, 30, 10),
      child: Column(
        children: [
          Row(
            children: [
              IconButton(
                onPressed: () {
                  setState(() {
                    _pickerMonth = _pickerMonth == 1 ? 12 : _pickerMonth - 1;
                    _pickerYear =
                        _pickerMonth == 12 ? _pickerYear - 1 : _pickerYear;
                    _selectedMonth = DateTime(_pickerYear, _pickerMonth, 1);
                  });
                },
                icon: Icon(Icons.navigate_before_rounded),
              ),
              Expanded(
                child: Center(
                    child: Text16(
                  text: _pickerYear.toString() +
                      '년 ' +
                      _pickerMonth.toString() +
                      '월',
                  bold: true,
                )),
              ),
              IconButton(
                onPressed: () {
                  setState(() {
                    _pickerMonth = _pickerMonth == 12 ? 1 : _pickerMonth + 1;
                    _pickerYear =
                        _pickerMonth == 1 ? _pickerYear + 1 : _pickerYear;
                    _selectedMonth = DateTime(_pickerYear, _pickerMonth, 1);
                  });
                },
                icon: Icon(Icons.navigate_next_rounded),
              ),
            ],
          ),
          // ...generateMonths(),
          SizedBox(
            height: 10.0,
          ),
        ],
      ),
    );
  }

  Widget _coin() {
    return Container(
      width: MediaQuery.of(context).size.width - 40,
      child: Padding(
        padding: const EdgeInsets.fromLTRB(20, 10, 20, 10),
        child: Column(
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text12(
                  text: _pickerMonth.toString() + '.',
                  textColor: myGrey,
                ),
                Text12(
                  text: '남은 코인',
                  textColor: myGrey,
                )
              ],
            ),
            SizedBox(height: 10),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text16(text: '업적달성 리워드', bold: true),
                Text16(
                    text: isplus ? '+ 500C' : '- 500C',
                    bold: true,
                    textColor: isplus ? myMainGreen : myRed)
              ],
            )
          ],
        ),
      ),
      decoration: myBoxDecoration,
    );
  }
}
