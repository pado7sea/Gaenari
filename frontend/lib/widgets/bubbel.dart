import 'package:flutter/material.dart';

class BubblePainter extends CustomPainter {
  final Color color;

  BubblePainter({required this.color});

  @override
  void paint(Canvas canvas, Size size) {
    var paint = Paint()
      ..color = color
      ..style = PaintingStyle.fill;

    var path = Path()
      ..moveTo(20, 0)
      ..lineTo(size.width - 20, 0)
      ..arcToPoint(Offset(size.width, 20), radius: Radius.circular(20))
      ..lineTo(size.width, size.height - 20)
      ..arcToPoint(Offset(size.width - 20, size.height),
          radius: Radius.circular(20))
      ..lineTo(size.width / 2 + 10, size.height)
      ..lineTo(size.width / 2, size.height + 10)
      ..lineTo(size.width / 2 - 10, size.height)
      ..lineTo(20, size.height)
      ..arcToPoint(Offset(0, size.height - 20), radius: Radius.circular(20))
      ..lineTo(0, 20)
      ..arcToPoint(Offset(20, 0), radius: Radius.circular(20))
      ..close();

    canvas.drawPath(path, paint);
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return false;
  }
}
