import 'package:flutter/material.dart';
import 'package:forsythia/models/program/programDetail.dart';
import 'package:forsythia/service/program_servise.dart';
import 'package:forsythia/theme/text.dart';
// import 'package:forsythia/widgets/largeAppBar.dart';
import 'package:forsythia/widgets/smallAppBar.dart';

class ProgramScreen extends StatefulWidget {
  const ProgramScreen({super.key});

  @override
  State<ProgramScreen> createState() => _ProgramScreenState();
}

class _ProgramScreenState extends State<ProgramScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: smallAppBar(title: '운동프로그램'),
      body: TextButton(
          onPressed: () {
            fetchAndPrintProgramDetail();
          },
          child: Text16(
            text: "눌러",
          )),
    );
  }

  void fetchAndPrintProgramDetail() async {
    try {
      // 프로그램 번호를 넘겨서 세부 정보를 가져옴
      programDetail detail = await ProgramServise.fetchProgramDetail(1);

      // 가져온 프로그램 세부 정보를 출력
      print('Status: ${detail.status}');
      print('Message: ${detail.message}');

      if (detail.data != null) {
        print('Program ID: ${detail.data!.programId}');
        print('Program Title: ${detail.data!.programTitle}');
        // 세부 정보 출력 계속...
      }
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text(
              '프로그램 ID: ${detail.data!.programId}\n프로그램 제목: ${detail.data!.programTitle}'), // 여기에 표시할 정보 추가
          duration: Duration(seconds: 3), // SnackBar가 표시될 시간 설정
        ),
      );
    } catch (e) {
      // 에러 처리
      print('Error: $e');
    }
  }
}
