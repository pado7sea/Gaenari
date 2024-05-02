import 'package:flutter/material.dart';
import 'package:forsythia/models/programs/ProgramDetail.dart';
import 'package:forsythia/service/program_service.dart';
import 'package:forsythia/widgets/SmallAppBar.dart';

class ProgramScreen extends StatefulWidget {
  const ProgramScreen({super.key});

  @override
  State<ProgramScreen> createState() => _ProgramScreenState();
}

class _ProgramScreenState extends State<ProgramScreen> {
  Future<ProgramDetail>? _programDetailFuture;

  @override
  void initState() {
    super.initState();
    _fetchProgramDetail(); // 위젯이 생성될 때 프로그램 디테일을 가져오는 함수를 호출해.
  }

  void _fetchProgramDetail() {
    _programDetailFuture = ProgramService.fetchProgramDetail(
        1); // 프로그램 디테일을 가져오는 함수 호출. number는 여기에 들어갈 값이야.
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SmallAppBar(title: '운동프로그램'),
      body: FutureBuilder<ProgramDetail>(
        future: _programDetailFuture, // Future 변수를 사용하여 FutureBuilder를 설정해.
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return CircularProgressIndicator(); // 데이터를 가져오는 중에는 로딩 표시기를 보여줘.
          } else if (snapshot.hasError) {
            return Text(
                'Error: ${snapshot.error}'); // 데이터를 가져오는 도중 에러가 발생하면 에러를 표시해.
          } else {
            // 데이터를 성공적으로 가져왔을 때는 가져온 데이터를 활용하여 위젯을 구성해.
            final programDetail = snapshot.data;
            return _buildProgramDetailWidget(
                programDetail); // 가져온 데이터를 표시하는 함수를 호출해.
          }
        },
      ),
    );
  }

  Widget _buildProgramDetailWidget(ProgramDetail? programDetail) {
    // 가져온 프로그램 디테일 데이터를 사용하여 위젯을 만들어.
    if (programDetail != null) {
      return Text('Program Name: ${programDetail.status}');
    } else {
      return Text(
          'No program detail available'); // 만약 가져온 데이터가 없으면 해당 메시지를 표시해.
    }
  }
}
