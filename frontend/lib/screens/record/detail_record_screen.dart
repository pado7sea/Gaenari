import 'package:flutter/material.dart';
import 'package:forsythia/widgets/small_app_bar.dart';

class DetailRecordScreen extends StatefulWidget {
  const DetailRecordScreen({super.key});

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
    );
  }
}
