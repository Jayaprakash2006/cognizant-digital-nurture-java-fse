import { CreditLabelPipe } from './credit-label.pipe';

describe('CreditLabelPipe', () => {
  let pipe: CreditLabelPipe;

  beforeEach(() => {
    pipe = new CreditLabelPipe();
  });

  it('should create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('should return "1 Credit" for 1', () => {
    expect(pipe.transform(1)).toBe('1 Credit');
  });

  it('should return "3 Credits" for 3', () => {
    expect(pipe.transform(3)).toBe('3 Credits');
  });

  it('should return "No Credits" for 0', () => {
    expect(pipe.transform(0)).toBe('No Credits');
  });

  it('should return "No Credits" for null', () => {
    expect(pipe.transform(null)).toBe('No Credits');
  });
});
