import 'package:flutter/material.dart';

class RecodScreen extends StatefulWidget {
  const RecodScreen({super.key});

  @override
  State<RecodScreen> createState() => _RecodScreenState();
}

class _RecodScreenState extends State<RecodScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
      title: Text('운동기록'),
    ));
  }
}
