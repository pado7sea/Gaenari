import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:forsythia/models/coins/coin_history.dart';
import 'package:forsythia/service/coin_service.dart';
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

  CoinRecord coinRecord = CoinRecord();
  MemberCoinRecordList memberCoinRecordList = MemberCoinRecordList();

  bool active = false;

  @override
  void initState() {
    super.initState();
    monthlyCoinRecord();
  }

  void monthlyCoinRecord() async {
    MonthlyCoinRecord monthlyCoinRecord;
    monthlyCoinRecord =
        await CoinService.fetchMonthlyCoinRecord(_pickerYear, _pickerMonth);
    setState(() {
      coinRecord = monthlyCoinRecord.data!;
      active = true;
    });
    // print('아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ아아아ㅏ아');
    // print(coinRecord.memberCoinRecordList![0].day);
    // print(coinRecord.memberCoinRecordList![0].isIncreased);
    // print(coinRecord.memberCoinRecordList![0].coinTitle);
    // print(coinRecord.memberCoinRecordList![0].coinAmount);
    // print(coinRecord.memberCoinRecordList![0].balance);
    // print('아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ아아아ㅏ아');
    // print(coinRecord.memberCoinRecordList!.length);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(title: '코인내역', back: true),
      body: active
          ? Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                _monthPicker(),
                SizedBox(
                  height: 20.0,
                ),
                Text(DateFormat.yMMMM().format(_selectedMonth)),
                _coin()
              ],
            )
          : Center(
              child: CircularProgressIndicator(),
            ),
    );
  }

  Widget _monthPicker() {
    return Padding(
      padding: const EdgeInsets.all(20),
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
        ],
      ),
    );
  }

  Widget _coin() {
    return Expanded(
      child: ShaderMask(
        shaderCallback: (Rect bounds) {
          return LinearGradient(
            begin: Alignment.center,
            end: Alignment.topCenter,
            colors: const [Colors.white, Color(0x00FFFFFF)],
            stops: const [0.95, 1],
            tileMode: TileMode.mirror,
          ).createShader(bounds);
        },
        child: ListView.builder(
          itemCount: coinRecord.memberCoinRecordList!.length,
          itemBuilder: (context, index) {
            return Padding(
              padding: const EdgeInsets.fromLTRB(20, 10, 20, 5),
              child: Container(
                width: MediaQuery.of(context).size.width - 40,
                child: Padding(
                  padding: const EdgeInsets.fromLTRB(20, 10, 20, 10),
                  child: Column(
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text12(
                            text: coinRecord.month.toString() +
                                '.' +
                                coinRecord.memberCoinRecordList![index].day
                                    .toString(),
                            textColor: myGrey,
                          ),
                          Row(
                            children: [
                              Text12(
                                text: coinRecord
                                        .memberCoinRecordList![index].balance
                                        .toString() +
                                    '  ',
                                textColor: myGrey,
                              ),
                              Image(
                                image: AssetImage(
                                    'assets/color_icons/icon_coin.png'),
                                width: 13,
                                height: 13,
                                fit: BoxFit.cover,
                                filterQuality: FilterQuality.none,
                              ),
                            ],
                          )
                        ],
                      ),
                      SizedBox(height: 10),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text16(
                              text: coinRecord
                                  .memberCoinRecordList![index].coinTitle
                                  .toString(),
                              bold: true),
                          if (coinRecord
                                  .memberCoinRecordList![index].isIncreased ==
                              true)
                            Text16(
                                text: '+' +
                                    coinRecord
                                        .memberCoinRecordList![index].coinAmount
                                        .toString(),
                                textColor: myMainGreen,
                                bold: true)
                          else
                            Text16(
                                text: '-' +
                                    coinRecord
                                        .memberCoinRecordList![index].coinAmount
                                        .toString(),
                                textColor: myRed,
                                bold: true)
                        ],
                      )
                    ],
                  ),
                ),
                decoration: myBoxDecoration,
              ),
            );
          },
        ),
      ),
    );
  }
}
